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
        items.add(titleItem)
        items.add(loadingItem)
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

    fun showLoading() {
        val initPosition = items.lastIndex
        if (items[initPosition].getViewType() != VIEW_TYPE_LOADING) {
            items.add(loadingItem)
            notifyItemInserted(getLastPosition())
        }
    }

    fun dismissLoading() {
        val initPosition = items.lastIndex
        if (items[initPosition].getViewType() == VIEW_TYPE_LOADING) {
            items.removeAt(initPosition)
            notifyItemRemoved(initPosition)
        }
    }

    fun addReports(reports: List<RejectReport>) {
//        val initPosition = items.lastIndex
//        dismissLoading()
//
//        items.addAll(reports)
//        notifyItemRangeChanged(initPosition, getLastPosition())

        // first remove loading and notify
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        // insert news and the loading at the end of the list
        items.addAll(reports)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1 /* plus loading item */)
    }

    fun clearAndAddReports(reports: List<RejectReport>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(reports)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    fun getReports(): List<RejectReport> =
            items
                    .filter { it.getViewType() == VIEW_TYPE_ITEM }
                    .map { it as RejectReport }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

}