package com.fr.fbsreport.ui.home.report.reject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.*
import com.fr.fbsreport.model.RejectReport
import kotlinx.android.synthetic.main.item_view_reject_report.view.*

class RejectReportAdapter(context: Context?) : BaseRecyclerAdapter<ViewType, RecyclerView.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_REJECT_REPORT_TITLE -> return RejectReportTitleViewHolder(inflater?.inflate(R.layout.item_view_reject_report_title, parent, false))
            VIEW_TYPE_ITEM -> return RejectReportViewHolder(inflater?.inflate(R.layout.item_view_reject_report, parent, false))
            VIEW_TYPE_REJECT_REPORT_TOTAL -> return RejectReportTotalViewHolder(inflater?.inflate(R.layout.item_view_reject_report_total, parent, false))
        }
        return RejectReportViewHolder(inflater?.inflate(R.layout.item_view_reject_report, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is RejectReportViewHolder) {
            holder.bind(items[position] as RejectReport)
            holder.itemView.view_underline.visibility = View.VISIBLE
            if (position == items.size - 2) {
                holder.itemView.view_underline.visibility = View.GONE
            }
        }
    }

    class RejectReportTitleViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    class ItemRejectReportTitle : ViewType {
        override fun getViewType(): Int {
            return VIEW_TYPE_REJECT_REPORT_TITLE
        }
    }

    class RejectReportViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(rejectReport: RejectReport) {
            itemView.txt_sale_num.text = rejectReport.saleNum
            itemView.txt_sale_date.text = rejectReport.getFormatDate()
            itemView.txt_total.text = rejectReport.getFormatTotal()
            itemView.txt_reason.text = rejectReport.discountReason
        }
    }

    class RejectReportTotalViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    class ItemRejectReportTotal(val total: Long) : ViewType {
        override fun getViewType(): Int {
            return VIEW_TYPE_REJECT_REPORT_TOTAL
        }
    }
}