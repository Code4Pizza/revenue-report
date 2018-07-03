package com.fr.fbsreport.model

import com.fr.fbsreport.base.VIEW_TYPE_ITEM
import com.fr.fbsreport.base.ViewType
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseReport(@SerializedName("sale_date")
                      var saleDate: String,
                      @SerializedName("sale_num")
                      var saleNum: String,
                      @SerializedName("total")
                      var total: Long) : ViewType, Serializable {

    override fun getViewType(): Int {
        return VIEW_TYPE_ITEM
    }
}