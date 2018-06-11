package com.fr.fbsreport.network

import com.google.gson.annotations.SerializedName

class BaseResponse {

    data class Default<T>(var data: T)
    
    data class Report<T>(
            var data: ArrayList<T>,
            var meta: Meta
    )

    data class Meta(
            var pagination: Pagination
    )

    data class Pagination(
            var total: Long,
            var count: Int,
            @SerializedName("per_page")
            var perPage: Int,
            @SerializedName("current_page")
            var currentPage: Int,
            @SerializedName("total_pages")
            var totalPages: Int,
            var links: Links)

    data class Links(var next: String)
}