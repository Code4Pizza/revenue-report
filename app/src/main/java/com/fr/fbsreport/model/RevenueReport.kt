package com.fr.fbsreport.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.extension.VIEW_TYPE_ITEM
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_revenue_report")
class RevenueReport(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @ColumnInfo(name = "sale_date")
        @SerializedName("sale_date")
        val saleDate: String,
        @ColumnInfo(name = "shift")
        val shift: String,
        @ColumnInfo(name = "shift_start")
        @SerializedName("shift_start")
        val shiftStart: String,
        @ColumnInfo(name = "shift_end")
        @SerializedName("shift_end")
        val shiftEnd: String,
        @ColumnInfo(name = "count_pax")
        @SerializedName("count_pax")
        val countPax: Long,
        @ColumnInfo(name = "total_sales")
        @SerializedName("total_sales")
        val totalSales: Long,
        @ColumnInfo(name = "drawer_total")
        @SerializedName("drawer_total")
        val drawerTotal: Long,
        @ColumnInfo(name = "over_short_amount")
        @SerializedName("over_short_amount")
        val overShortAmount: Long) : ViewType {

    override fun getViewType(): Int {
        return VIEW_TYPE_ITEM
    }
}