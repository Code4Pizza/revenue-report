package com.fr.fbsreport.ui.report.item

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.formatWithDot
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.ItemReport
import com.fr.fbsreport.ui.report.BaseReportAdapter
import kotlinx.android.synthetic.main.item_view_item_report.view.*

class ItemReportDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ItemReportViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, listener: BaseReportAdapter.OnReportClickListener?) {
        if (holder is ItemReportViewHolder) {
            holder.bind(item as ItemReport)
            holder.itemView.setOnClickListener { listener?.onReportClick(item) }
        }
    }

    inner class ItemReportViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_view_item_report)) {

        fun bind(itemReport: ItemReport) {
            itemView.txt_name.text = itemReport.name
            itemView.txt_total.text = itemReport.total.formatWithDot()
        }
    }
}