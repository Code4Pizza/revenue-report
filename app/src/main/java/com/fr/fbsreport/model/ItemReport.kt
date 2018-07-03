package com.fr.fbsreport.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "tbl_item_report")
class ItemReport(@PrimaryKey(autoGenerate = true) val id: Int,
                 saleDate: String,
                 saleNum: String,
                 total: Long,
                 @ColumnInfo(name = "discount")
                 var discount: Int,
//        @ColumnInfo(name = "items")
//        var items: List<Item>,
                 @ColumnInfo(name = "items_total")
                 @SerializedName("items_total")
                 var itemsTotal: Long
) : BaseReport(saleDate, saleNum, total), Serializable