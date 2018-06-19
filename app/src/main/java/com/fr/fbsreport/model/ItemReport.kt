package com.fr.fbsreport.model

import com.google.gson.annotations.SerializedName

@Suppress("unused")
class ItemReport(
        saleDate: String,
        saleNum: String,
        total: Long,
        var discount: Int,
        var items: List<Item>,
        @SerializedName("items_total")
        var itemsTotal: Long
) : BaseReport(saleDate, saleNum, total)