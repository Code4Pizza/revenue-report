package com.fr.fbsreport.network

import android.text.TextUtils
import com.fr.fbsreport.source.UserPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RestClient {

    companion object {
        private const val OAUTH_ERROR_HEADER = "error"
        private const val OAUTH_INVALID_TOKEN = "invalid_token"
        private const val WWW_AUTHENTICATE = "WWW-Authenticate"

        private var okHttpClient: OkHttpClient? = null
        private var callAdapterFactory: CallAdapter.Factory? = null

        fun createService(): AppService {
            return Retrofit.Builder()
                    .baseUrl("http://api.reporter.demo.ngocnh.info")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build()
                    .create(AppService::class.java)
        }

        private fun getOkHttpClient(): OkHttpClient {
            return okHttpClient ?: OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(AuthorizationInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                    .authenticator { _, _ -> null }
                    .build()
        }
    }


    private class AuthorizationInterceptor : Interceptor {

        private val refreshToken: String? = UserPreference.instance.getTokenModel()?.refreshToken

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            builder.header("Accept", "application/json")

            val accessToken = UserPreference.instance.getTokenModel()?.accessToken
            setAuthHeader(builder, accessToken)

            val response = chain.proceed(builder.build())

            if (isTokenExpiredError(response)) {
                synchronized(this) {
                    //TODO call refresh token   
                }
                return chain.proceed(builder.build())
            }
            return response
        }

        private fun setAuthHeader(builder: Request.Builder, token: String?) {
            // Add Auth token to each request if authorized
            if (!TextUtils.isEmpty(token)) {
                builder.header("Authorization", String.format("Bearer %s", token))
            }
        }

        private fun isTokenExpiredError(response: Response): Boolean {
            if (response.code() != 401) {
                return false
            }
            val authStr = response.header(WWW_AUTHENTICATE)
            if (!TextUtils.isEmpty(authStr)) {
                val authStrs = authStr!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (str in authStrs) {
                    if (isInvalidTokenError(str)) {
                        return true
                    }
                }
            }
            return false
        }

        private fun isInvalidTokenError(str: String): Boolean {
            val parts = str.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return if (parts.size == 2) {
                OAUTH_ERROR_HEADER.equals(parts[0].trim { it <= ' ' }, ignoreCase = true) && OAUTH_INVALID_TOKEN.equals(parts[1].replace("\"", "").trim { it <= ' ' }, ignoreCase = true)
            } else false
        }
    }
}