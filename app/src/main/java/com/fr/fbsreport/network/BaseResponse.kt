package com.fr.fbsreport.network

import com.fr.fbsreport.model.BaseReport
import com.google.gson.annotations.SerializedName

class BaseResponse {

    data class Default<T>(var data: T)

    data class Report<T : BaseReport>(
            var data: ArrayList<T>,
            var meta: Meta
    )

    data class Meta(
            @SerializedName("total_page")
            var totalPage: Long,
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
            var totalPages: Int)

    data class Links(var next: String)
}