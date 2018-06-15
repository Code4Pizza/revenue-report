package com.fr.fbsreport.ui.home.report.item.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.ItemReport
import kotlinx.android.synthetic.main.item_view_item_report.view.*

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

        fun bind(itemReport: ItemReport) {
            itemView.txt_sale_num.text = if (itemReport.saleNum.isEmpty()) "Unknown" else itemReport.saleNum
            itemView.txt_sale_date.text = itemReport.getFormatDate()
            itemView.txt_total.text = itemReport.getFormatTotal()
        }
    }
}