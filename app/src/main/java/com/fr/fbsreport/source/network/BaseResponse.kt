package com.fr.fbsreport.source.network

import com.fr.fbsreport.base.ViewType
import com.google.gson.annotations.SerializedName
import java.util.*

data class DataResponse<T>(var data: T)

data class ReportResponse<T : ViewType>(
        var data: List<T>,
        var meta: Meta?
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

data class Dashboard(
        @SerializedName("total_money")
        val totalMoney: Long,
        @SerializedName("total_bill")
        val totalBill: Int,
        val charts: ArrayList<Chart>,
        val sections: ArrayList<Section?>)

data class Chart(var time: String, var total: String)

data class Section(val name: String, var reports: ArrayList<ReportChart>)

data class ReportChart(
        // For bill
        val id: Int,
        val title: String,
        val total: String,
        // For item
        val name: String,
        val price: Int,
        val quantity: String)