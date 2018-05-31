package com.fr.fbsreport.model

import com.fr.fbsreport.base.BaseItem

data class RejectReport(
        var saleDate: String,
        var saleNum: String,
        var total: Long,
        var deleteReason: String
) : BaseItem()