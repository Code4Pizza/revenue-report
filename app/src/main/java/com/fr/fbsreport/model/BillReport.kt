package com.fr.fbsreport.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_bill_report")
class BillReport(@PrimaryKey(autoGenerate = true) val id: Int,
                 saleDate: String,
                 saleNum: String,
                 total: Long,
                 @ColumnInfo(name = "table_id")
                 @SerializedName("table_id")
                 var tableId: Int,
                 @ColumnInfo(name = "sub_total")
                 @SerializedName("sub_total")
                 var subTotal: Long,
                 @ColumnInfo(name = "discount")
                 var discount: Int,
                 @ColumnInfo(name = "tax")
                 var tax: Int,
                 @ColumnInfo(name = "service_charge_amount")
                 @SerializedName("service_charge_amount")
                 var serviceChargeAmount: Int
) : BaseReport(saleDate, saleNum, total)