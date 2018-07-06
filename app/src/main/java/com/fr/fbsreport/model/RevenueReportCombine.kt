package com.fr.fbsreport.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.extension.VIEW_TYPE_ITEM
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "tbl_revenue_report")
class RevenueReportCombine : ViewType, Serializable {
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
    @ColumnInfo(name = "sale_date")
    @SerializedName("sale_date")
    var saleDate: String = ""
    @ColumnInfo(name = "shift_1")
    var shift1: String = ""
    @ColumnInfo(name = "shift_1_start")
    var shift1Start: String = ""
    @ColumnInfo(name = "shift_1_end")
    var shift1End: String = ""
    @ColumnInfo(name = "count_1_pax")
    var count1Pax: Long = 0
    @ColumnInfo(name = "total_1_sales")
    var total1Sales: Long = 0
    @ColumnInfo(name = "drawer_1_total")
    var drawer1Total: Long = 0
    @ColumnInfo(name = "over_short_amount_1")
    var overShortAmount1: Long = 0
    @ColumnInfo(name = "shift_2")
    var shift2: String = ""
    @ColumnInfo(name = "shift_2_start")
    var shift2Start: String = ""
    @ColumnInfo(name = "shift_2_end")
    var shift2End: String = ""
    @ColumnInfo(name = "count_2_pax")
    var count2Pax: Long = 0
    @ColumnInfo(name = "total_2_sales")
    var total2Sales: Long = 0
    @ColumnInfo(name = "drawer_2_total")
    var drawer2Total: Long = 0
    @ColumnInfo(name = "over_short_amount_2")
    var overShortAmount2: Long = 0

    override fun getViewType(): Int {
        return VIEW_TYPE_ITEM
    }
}