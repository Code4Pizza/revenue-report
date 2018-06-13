package com.fr.fbsreport.ui.home.report.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.RejectReport
import kotlinx.android.synthetic.main.item_view_reject_report.view.*

class DeleteReportDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return DeleteReportViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as DeleteReportViewHolder
        holder.bind(item as RejectReport)
    }

    inner class DeleteReportViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_view_reject_report)) {

        fun bind(rejectReport: RejectReport) {
            itemView.txt_sale_num.text = rejectReport.saleNum
            itemView.txt_sale_date.text = rejectReport.getFormatDate()
            itemView.txt_total.text = rejectReport.getFormatTotal()
            itemView.txt_reason.text = rejectReport.discountReason
        }
    }
}