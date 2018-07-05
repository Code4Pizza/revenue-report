package com.fr.fbsreport.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.extension.VIEW_TYPE_ITEM

@Entity(tableName = "tbl_branch")
data class Branch(@PrimaryKey val id: Int,
                  @ColumnInfo(name = "code")
                  val code: String,
                  @ColumnInfo(name = "name")
                  val name: String,
                  @ColumnInfo(name = "address")
                  val address: String,
                  @ColumnInfo(name = "city")
                  val city: String,
                  @ColumnInfo(name = "state")
                  val state: String,
                  @ColumnInfo(name = "zip")
                  val zip: String,
                  @ColumnInfo(name = "country")
                  val country: String,
                  @ColumnInfo(name = "company")
                  val company: String,
                  @ColumnInfo(name = "status")
                  val status: Int) : ViewType {

    override fun getViewType(): Int {
        return VIEW_TYPE_ITEM
    }
}