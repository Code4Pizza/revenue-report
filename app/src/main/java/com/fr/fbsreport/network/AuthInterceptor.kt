package com.fr.fbsreport.network

import android.text.TextUtils
import com.fr.fbsreport.source.UserPreference
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(private val userPreference: UserPreference) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.header("Accept", "application/json")

        val accessToken = userPreference.getTokenModel()?.accessToken
        setAuthHeader(builder, accessToken)

        return chain.proceed(builder.build())
    }

    private fun setAuthHeader(builder: Request.Builder, token: String?) {
        // Add Auth token to each request if authorized
        if (!TextUtils.isEmpty(token)) {
            builder.header("authorization", String.format("Bearer %s", token))
        }
    }
}