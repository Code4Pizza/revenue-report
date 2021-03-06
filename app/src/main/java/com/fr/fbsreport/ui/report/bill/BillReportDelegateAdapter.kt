package com.fr.fbsreport.ui.report.bill

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.displayDate
import com.fr.fbsreport.extension.formatWithDot
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.BillReport
import com.fr.fbsreport.ui.report.BaseReportAdapter
import kotlinx.android.synthetic.main.item_view_bill_report.view.*

class BillReportDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return BillReportViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, listener: BaseReportAdapter.OnReportClickListener?) {
        if (holder is BillReportViewHolder) {
            holder.bind(item as BillReport)
            holder.itemView.setOnClickListener { listener?.onReportClick(item) }
        }
    }

    inner class BillReportViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_view_bill_report)) {

        fun bind(billReport: BillReport) {
            itemView.txt_sale_num.text = billReport.saleNum
            itemView.txt_sale_date.text = billReport.saleDate.displayDate()
            itemView.txt_table.text = billReport.tableId.toString()
            itemView.txt_total.text = billReport.subTotal.formatWithDot()
        }
    }
}