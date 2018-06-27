package com.fr.fbsreport.model

import com.fr.fbsreport.base.VIEW_TYPE_ITEM
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.utils.formatWithDot
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

open class BaseReport(@SerializedName("sale_date")
                      var saleDate: String,
                      @SerializedName("sale_num")
                      var saleNum: String,
                      @SerializedName("total")
                      var total: Long) : ViewType, Serializable {

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
            total.formatWithDot()
        } catch (e: Exception) {
            null
        }
    }

    override fun getViewType(): Int {
        return VIEW_TYPE_ITEM
    }
}