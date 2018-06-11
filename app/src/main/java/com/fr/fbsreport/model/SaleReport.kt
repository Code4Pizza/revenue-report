package com.fr.fbsreport.model

@Suppress("unused")
class SaleReport(
        saleDate: String,
        saleNum: String,
        total: Long,
        var discount: Long
) : BaseReport(saleDate, saleNum, total)