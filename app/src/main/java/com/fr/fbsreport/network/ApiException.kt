package com.fr.fbsreport.network

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.io.IOException

class ApiException : RuntimeException {

    companion object {
        fun httpError(response: Response<*>): ApiException {
            val message = response.code().toString() + " " + response.message()
            return ApiException(message, response)
        }

        fun networkError(exception: IOException): ApiException {
            return ApiException(exception.message, Type.NETWORK_ERROR, exception)
        }

        fun criticalError(exception: Throwable): ApiException {
            return ApiException(exception.message, Type.CRITICAL_ERROR, exception)
        }
    }

    class ErrorModel(var message: String, @SerializedName("status_code") var statusCode: Int)

    private var error: ErrorModel? = null
    var type: Type? = null

    constructor(message: String, response: Response<*>) : super(message) {
        try {
            val responseBody = response.errorBody()
            var bodyString: String? = null
            if (responseBody != null) {
                bodyString = responseBody.string()
                // ResponseBody require explicit close to prevent leak
                responseBody.close()
            }
            if (!bodyString.isNullOrEmpty()) {
                val error = Gson().fromJson<ErrorModel>(bodyString, ErrorModel::class.java)
                if (error != null) {
                    this.error = error
                    this.type = when (error.statusCode) {
                        401 -> Type.OAUTH_ERROR
                        in 401..499 -> Type.INVALID_REQUEST
                        in 500..599 -> Type.INTERNAL_SERVER_ERROR
                        else -> Type.UNEXPECTED_ERROR
                    }
                }
            }
        } catch (e: Exception) {
            // Skip
        }
    }

    constructor(message: String?, type: Type, exception: Throwable) : super(message, exception) {
        this.type = type
    }

    enum class Type {
        /**
         * Invalid user credentials
         */
        INVALID_USER_CREDENTIALS,
        /**
         * Invalid refresh token
         */
        INVALID_REFRESH_TOKEN,
        /**
         * Invalid refresh token
         */
        INVALID_ACCESS_TOKEN,
        /**
         * The client credentials are invalid
         */
        INVALID_CLIENT_CREDENTIALS,
        /**
         * Any other OAuth error
         */
        OAUTH_ERROR,
        /**
         * Request params or method are not valid
         */
        BUSINESS_ERROR,
        /**
         * Request is not valid
         */
        INVALID_REQUEST,
        /**
         * Internal server error
         */
        INTERNAL_SERVER_ERROR,
        /** An [IOException] occurred while communicating to the server.  */
        NETWORK_ERROR,

        UNEXPECTED_ERROR,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        CRITICAL_ERROR;
    }

}