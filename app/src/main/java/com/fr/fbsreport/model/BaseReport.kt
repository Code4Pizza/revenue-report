package com.fr.fbsreport.model

import com.fr.fbsreport.base.BaseItem
import com.fr.fbsreport.utils.formatWithDot
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

open class BaseReport(@SerializedName("sale_date")
                      var saleDate: String,
                      @SerializedName("sale_num")
                      var saleNum: String,
                      @SerializedName("total")
                      var total: Long) : BaseItem() {

    fun getFormatDate(): String? {
        return try {
            val date = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).parse(saleDate)
            SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(date)
        } catch (e: Exception) {
            null
        }
    }

    fun getFormatTotal(): String? {
        return try {
            total.formatWithDot() + "đ"
        } catch (e: Exception) {
            null
        }
    }
}