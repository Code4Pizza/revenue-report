package com.fr.fbsreport.source.network

import com.fr.fbsreport.base.ViewType
import com.google.gson.annotations.SerializedName
import java.util.*

data class DataResponse<T>(val data: T)

data class ReportResponse<T : ViewType>(
        val data: List<T>,
        val meta: Meta?
)

data class Meta(
        @SerializedName("total_page")
        val totalPage: Long,
        val pagination: Pagination
)

data class Pagination(
        val total: Long,
        val count: Int,
        @SerializedName("per_page")
        val perPage: Int,
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("total_pages")
        val totalPages: Int)

data class Dashboard(
        @SerializedName("total_money")
        val totalMoney: Long,
        @SerializedName("total_bill")
        val totalBill: Int,
        val charts: ArrayList<Chart>,
        val sections: ArrayList<Section?>)

data class Chart(val time: String, val total: String)

data class Section(val name: String, val reports: ArrayList<ReportChart>, val type : String)

data class ReportChart(
        // For bill
        val id: Int,
        val title: String,
        val total: String,
        // For item
        val name: String,
        val price: Int,
        val quantity: String)