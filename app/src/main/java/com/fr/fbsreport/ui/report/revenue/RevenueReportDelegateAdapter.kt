package com.fr.fbsreport.ui.report.revenue

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.formatWithDot
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.RevenueReportCombine
import com.fr.fbsreport.ui.report.BaseReportAdapter
import kotlinx.android.synthetic.main.item_view_revenue_report.view.*

class RevenueReportDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ItemReportViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, listener: BaseReportAdapter.OnReportClickListener?) {
        if (holder is ItemReportViewHolder) {
            holder.bind(item as RevenueReportCombine)
            holder.itemView.setOnClickListener { listener?.onReportClick(item) }
        }
    }

    inner class ItemReportViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_view_revenue_report)) {

        fun bind(itemReport: RevenueReportCombine) {
            itemView.txt_sale_date.text = itemReport.saleDate
            itemView.txt_shift_1_time.text = itemReport.shift1Start + "-" + itemReport.shift1End
            itemView.txt_shift_2_time.text = itemReport.shift2Start + "-" + itemReport.shift2End
            itemView.txt_shift_1_sales.text = itemReport.total1Sales.formatWithDot()
            itemView.txt_shift_2_sales.text = itemReport.total2Sales.formatWithDot()
        }
    }
}