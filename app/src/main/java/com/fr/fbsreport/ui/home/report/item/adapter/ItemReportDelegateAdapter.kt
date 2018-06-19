package com.fr.fbsreport.ui.home.report.item.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.Item
import com.fr.fbsreport.model.ItemReport
import com.fr.fbsreport.utils.formatWithDot
import kotlinx.android.synthetic.main.item_view_item_report.view.*
import kotlinx.android.synthetic.main.view_item_category.view.*

class ItemReportDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ItemReportViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as ItemReportViewHolder
        holder.bind(item as ItemReport)
    }

    inner class ItemReportViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_view_item_report)) {

        private var inflater: LayoutInflater = LayoutInflater.from(itemView.context)

        fun bind(itemReport: ItemReport) {
            itemView.txt_sale_num.text = if (itemReport.saleNum.isEmpty()) "Unknown" else itemReport.saleNum
            itemView.txt_sale_date.text = itemReport.getFormatDate()
            itemView.txt_discount.text = itemReport.discount.formatWithDot()
            itemView.findViewById<TextView>(R.id.txt_total).text = itemReport.total.formatWithDot()
            itemView.ll_items.removeAllViewsInLayout()
            for (item in itemReport.items) {
                addView(item)
            }
        }

        private fun addView(item: Item) {
            val itemCategory = inflater.inflate(R.layout.view_item_category, null, false)
            itemCategory.txt_category.text = item.category
            itemCategory.txt_item_code.text = item.itemCode
            itemCategory.txt_unit.text = item.unit
            itemCategory.txt_quantity.text = item.quantity.toString()
            itemCategory.txt_price.text = item.price.formatWithDot()
            itemCategory.txt_vat.text = item.tax.formatWithDot()
            itemCategory.findViewById<TextView>(R.id.txt_total).text = item.total.formatWithDot()
            itemView.ll_items.addView(itemCategory)
        }
    }
}