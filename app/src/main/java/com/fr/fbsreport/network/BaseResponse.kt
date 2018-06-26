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

    data class Sections(val section1: Section1, val section2: Section2)

    data class Section1(val shifts: Shifts, val total: Total)

    data class Shifts(
            @SerializedName("1")
            val shift1: Shift,
            @SerializedName("2")
            val shift2: Shift)

    data class Shift(
            @SerializedName("sale_nume")
            var saleNum: Int,
            var total: Long,
            var sales: ArrayList<Sale>)

    data class Sale(var id: Int,
                    @SerializedName("sale_nume")
                    var saleNum: Int,
                    @SerializedName("sale_date")
                    var saleDate: String,
                    var discount: Int,
                    var tableId: Int,
                    @SerializedName("discount_reason")
                    var discountReason: String,
                    @SerializedName("delete_reason")
                    var deleteReason: String,
                    var total: Long,
                    @SerializedName("sub_total")
                    var subTotal: Long,
                    var tax: Int,
                    @SerializedName("service_charge_amount")
                    var serviceChargeAmount: Int,
                    @SerializedName("branch_id")
                    var branchId: Int,
                    var type: String)

    data class Total(var saleNum: Int, var sales: Long)

    data class Section2(var section2: ArrayList<Section2Data>)

    data class Section2Data(
            @SerializedName("table_id")
            var tableId: Int,
            @SerializedName("total_sales")
            var totalSales: String)
}