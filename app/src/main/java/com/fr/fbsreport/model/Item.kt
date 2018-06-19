package com.fr.fbsreport.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Eric on 6/19/2018.
 */
data class Item(var category: String,
                @SerializedName("item_code")
                var itemCode: String,
                var unit: String,
                var quantity: Int,
                var price: Int,
                var total: Long,
                var discount: Int,
                @SerializedName("service_charge_amount")
                var serviceChargeAmount: Int,
                var tax: Int)