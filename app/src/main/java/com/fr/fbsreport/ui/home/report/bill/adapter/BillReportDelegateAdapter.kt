package com.fr.fbsreport.ui.home.report.bill.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.BillReport
import kotlinx.android.synthetic.main.item_view_bill_report.view.*

class BillReportDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return BillReportViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as BillReportViewHolder
        holder.bind(item as BillReport)
    }

    inner class BillReportViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_view_bill_report)) {

        fun bind(billReport: BillReport) {
            itemView.txt_sale_num.text = if (billReport.saleNum.isEmpty()) "Unknown" else billReport.saleNum
            itemView.txt_sale_date.text = billReport.getFormatDate()
            itemView.txt_total.text = billReport.getFormatTotal()
            itemView.txt_reason.text = billReport.serviceChargeAmount.toString()
        }
    }
}