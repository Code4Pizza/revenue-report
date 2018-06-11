package com.fr.fbsreport.model

import com.google.gson.annotations.SerializedName

@Suppress("unused")
class BillReport(
        saleDate: String,
        saleNum: String,
        total: Long,
        @SerializedName("table_id")
        var tableId: Int,
        @SerializedName("sub_total")
        var subTotal: Long,
        var discount: Long,
        var tax: Int,
        @SerializedName("service_charge_amount")
        var serviceChargeAmount: Int
) : BaseReport(saleDate, saleNum, total)