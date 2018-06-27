package com.fr.fbsreport.network

import com.fr.fbsreport.model.BaseReport
import com.google.gson.annotations.SerializedName

data class DataResponse<T>(var data: T)

data class ReportResponse<T : BaseReport>(
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

data class Dashboard(var charts: ArrayList<String>, var sections: ArrayList<Section?>)

data class Section(val name: String, var reports: ArrayList<ReportChart>)

data class ReportChart(val id: Int, val title: String, val total: String)