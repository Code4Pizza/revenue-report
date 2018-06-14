package com.fr.fbsreport.ui.home.report.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.base.*
import com.fr.fbsreport.model.RejectReport

class DeleteReportAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private val titleItem = object : ViewType {
        override fun getViewType() = VIEW_TYPE_REJECT_REPORT_TITLE
    }

    private val loadingItem = object : ViewType {
        override fun getViewType() = VIEW_TYPE_LOADING
    }

    init {
        delegateAdapters.put(VIEW_TYPE_LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(VIEW_TYPE_REJECT_REPORT_TITLE, DeleteReportTitleDelegateAdapter())
        delegateAdapters.put(VIEW_TYPE_ITEM, DeleteReportDelegateAdapter())
        items = ArrayList()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addReports(reports: ArrayList<RejectReport>) {
        val initPosition = items.size + 1
        items.addAll(reports)
        notifyItemRangeInserted(initPosition, items.size)
    }

    fun getReports(): List<RejectReport> =
            items
                    .filter { it.getViewType() == VIEW_TYPE_ITEM }
                    .map { it as RejectReport }
}