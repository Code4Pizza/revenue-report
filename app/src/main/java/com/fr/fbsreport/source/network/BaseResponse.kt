package com.fr.fbsreport.source.network

import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.extension.VIEW_TEST_1
import com.fr.fbsreport.extension.VIEW_TEST_2
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
        val sections: ArrayList<Section?>) : ViewType {
    override fun getViewType() = VIEW_TEST_1
}

data class Chart(val time: String, val total: String)

data class Section(val name: String, val reports: ArrayList<ReportChart>, val type: String) : ViewType {
    override fun getViewType() = VIEW_TEST_2
}

data class ReportChart(
        // Doanh so theo ca
        val id: Int,
        val title: String,
        @SerializedName("count_pax")
        val countPax: Int,
        @SerializedName("count_receipt")
        val countReceipt: Int,
        @SerializedName("total_wo_tax")
        val totalWoTax: String,
        val tax: String,
        @SerializedName("total_cancel")
        val totalCancel: String,
        @SerializedName("total_discount")
        val totalDiscount: String,
        val total: String,
        @SerializedName("shift_start")
        val shiftStart: Shift,
        @SerializedName("shift_end")
        val shiftEnd: Shift,

        // Mat hang ban chay
        val name: String,
        val price: Int,
        val quantity: String,

        // Hoa don giam gia
        val discount: String,

        // Hoa don huy
        @SerializedName("sale_num")
        val saleNum: String,
        val reason: String,

        // Hoa don
        val customer: String
)

data class Shift(val date: String)