package com.fr.fbsreport.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.extension.VIEW_TYPE_ITEM
import java.io.Serializable

@Entity(tableName = "tbl_item_report")
class ItemReport(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @ColumnInfo(name = "code")
        val code: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "category")
        val category: String,
        @ColumnInfo(name = "quantity")
        val quantity: Int,
        @ColumnInfo(name = "price")
        val price: Int,
        @ColumnInfo(name = "discount")
        val discount: Int,
        @ColumnInfo(name = "tax")
        val tax: Int,
        @ColumnInfo(name = "total")
        val total: Int) : ViewType, Serializable {

    override fun getViewType(): Int {
        return VIEW_TYPE_ITEM
    }
}