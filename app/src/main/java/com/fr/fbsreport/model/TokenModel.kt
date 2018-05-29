package com.fr.fbsreport.model

import com.google.gson.annotations.SerializedName

data class TokenModel(
        @SerializedName("access_token")
        var accessToken: String?,
        @SerializedName("token_type")
        var tokenType: String?,
        @SerializedName("expires_in")
        var expiresIn: Int?,
        @SerializedName("refresh_token")
        var refreshToken: String?,
        @SerializedName("expire_time")
        var expireTime: Int?) 