package com.fr.fbsreport.ui.report.delete.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseReportAdapter
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.DeleteReport
import kotlinx.android.synthetic.main.item_view_delete_report.view.*

class DeleteReportDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return DeleteReportViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, listener: BaseReportAdapter.OnReportClickListener?) {
        if (holder is DeleteReportViewHolder) {
            holder.bind(item as DeleteReport)
            holder.itemView.setOnClickListener { listener?.onReportClick(item) }
        }
    }

    inner class DeleteReportViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_view_delete_report)) {

        fun bind(rejectReport: DeleteReport) {
            itemView.txt_sale_num.text = if (rejectReport.saleNum.isEmpty()) "Unknown" else rejectReport.saleNum
            itemView.txt_sale_date.text = rejectReport.getFormatDate()
            itemView.txt_total.text = rejectReport.getFormatTotal()
            itemView.txt_reason.text = rejectReport.discountReason
        }
    }
}