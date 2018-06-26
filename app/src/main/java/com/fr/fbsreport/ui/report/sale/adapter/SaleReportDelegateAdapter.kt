package com.fr.fbsreport.ui.report.sale.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseReportAdapter
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.SaleReport
import com.fr.fbsreport.utils.formatWithDot
import kotlinx.android.synthetic.main.item_view_sale_report.view.*

class SaleReportDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return SaleReportViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, listener: BaseReportAdapter.OnReportClickListener?) {
        if (holder is SaleReportViewHolder) {
            holder.bind(item as SaleReport)
            holder.itemView.setOnClickListener { listener?.onReportClick(item) }
        }
    }

    inner class SaleReportViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_view_sale_report)) {

        fun bind(saleReport: SaleReport) {
            itemView.txt_sale_num.text = if (saleReport.saleNum.isEmpty()) "Unknown" else saleReport.saleNum
            itemView.txt_sale_date.text = saleReport.getFormatDate()
            itemView.txt_total.text = saleReport.total.formatWithDot()
            itemView.txt_discount.text = saleReport.discount.formatWithDot()
        }
    }
}