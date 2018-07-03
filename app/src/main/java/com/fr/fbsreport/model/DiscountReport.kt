package com.fr.fbsreport.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_discount_report")
class DiscountReport(@PrimaryKey(autoGenerate = true) val id: Int,
                     saleDate: String,
                     saleNum: String,
                     total: Long,
                     @ColumnInfo(name = "discount")
                     val discount: Long,
                     @ColumnInfo(name = "discount_reason")
                     @SerializedName("discount_reason")
                     val discountReason: String?
) : BaseReport(saleDate, saleNum, total)