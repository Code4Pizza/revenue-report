package com.fr.fbsreport.model

@Suppress("unused")
class ItemReport(
        saleDate: String,
        saleNum: String,
        total: Long
) : BaseReport(saleDate, saleNum, total)