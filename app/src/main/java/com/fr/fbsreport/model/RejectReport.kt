package com.fr.fbsreport.model

import com.google.gson.annotations.SerializedName

@Suppress("unused")
class RejectReport(
        saleDate: String,
        saleNum: String,
        total: Long,
        @SerializedName("discount_reason")
        var discountReason: String
) : BaseReport(saleDate, saleNum, total)