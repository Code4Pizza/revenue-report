package com.fr.fbsreport.model

import com.fr.fbsreport.base.VIEW_TYPE_ITEM
import com.fr.fbsreport.base.ViewType

data class Branch(val id: Int, val code: String, val name: String, val address: String, val city: String,
                  val state: String, val zip: String, val country: String, val company: String, val status: Int) : ViewType {

    override fun getViewType(): Int {
        return VIEW_TYPE_ITEM
    }
}