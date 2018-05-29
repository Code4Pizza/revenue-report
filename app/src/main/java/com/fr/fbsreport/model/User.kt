package com.fr.fbsreport.model

import com.google.gson.annotations.SerializedName

data class User(
        var id: Int?,
        var username: String?,
        var email: String?,
        @SerializedName("last_visited")
        var lastVisited: String?,
        var type: String?,
        var status: Int?) 