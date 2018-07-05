package com.fr.fbsreport.ui.report.discount

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.displayDate
import com.fr.fbsreport.extension.formatWithDot
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.DiscountReport
import com.fr.fbsreport.ui.report.BaseReportAdapter
import kotlinx.android.synthetic.main.item_view_discount_report.view.*

class DiscountReportDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return SaleReportViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, listener: BaseReportAdapter.OnReportClickListener?) {
        if (holder is SaleReportViewHolder) {
            holder.bind(item as DiscountReport)
            holder.itemView.setOnClickListener { listener?.onReportClick(item) }
        }
    }

    inner class SaleReportViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_view_discount_report)) {

        fun bind(discountReport: DiscountReport) {
            itemView.txt_sale_num.text = discountReport.saleNum
            itemView.txt_sale_date.text = discountReport.saleDate.displayDate()
            itemView.txt_discount.text = discountReport.discount.formatWithDot()
            itemView.txt_discount_reason.text = discountReport.discountReason
        }
    }
}