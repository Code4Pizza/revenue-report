package com.fr.fbsreport.network

import android.content.Context
import android.widget.Toast

class ErrorUtils {
    companion object {
        fun handleCommonError(context: Context, throwable: Throwable) {
            if (throwable !is ApiException) {
                Toast.makeText(context, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
                return
                // throw RuntimeException("Unhandled exception", throwable)
            }
            when (throwable.type) {
                ApiException.Type.CRITICAL_ERROR -> throw RuntimeException("Critical exception", throwable)
                ApiException.Type.NETWORK_ERROR -> {
                    Toast.makeText(context, "Fail to connect to server. Please try again.", Toast.LENGTH_SHORT).show()
                }
//                ApiException.Type.INVALID_USER_CREDENTIALS -> TODO()
//                ApiException.Type.INVALID_REFRESH_TOKEN -> TODO()
//                ApiException.Type.INVALID_ACCESS_TOKEN -> TODO()
//                ApiException.Type.INVALID_CLIENT_CREDENTIALS -> TODO()
//                ApiException.Type.OAUTH_ERROR -> TODO()
//                ApiException.Type.BUSINESS_ERROR -> TODO()
//                ApiException.Type.INVALID_REQUEST -> TODO()
                ApiException.Type.INTERNAL_SERVER_ERROR -> {
                    Toast.makeText(context, "There was a server error handling your request. Please try again.", Toast.LENGTH_SHORT).show()
                }
//                ApiException.Type.UNEXPECTED_ERROR -> TODO()
                else -> {
                    Toast.makeText(context, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}