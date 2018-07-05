package com.fr.fbsreport.model

import android.arch.persistence.room.ColumnInfo
import com.fr.fbsreport.extension.VIEW_TYPE_ITEM
import com.fr.fbsreport.base.ViewType
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseReport(
        @ColumnInfo(name = "sale_date")
        @SerializedName("sale_date")
        val saleDate: String,
        @ColumnInfo(name = "sale_num")
        @SerializedName("sale_num")
        val saleNum: String,
        @ColumnInfo(name = "total")
        @SerializedName("total")
        val total: Long) : ViewType, Serializable {

    override fun getViewType(): Int {
        return VIEW_TYPE_ITEM
    }
}