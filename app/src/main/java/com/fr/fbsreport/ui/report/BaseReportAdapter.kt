package com.fr.fbsreport.ui.report

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.VIEW_TYPE_ITEM
import com.fr.fbsreport.extension.VIEW_TYPE_LOADING
import com.fr.fbsreport.model.BaseReport

class BaseReportAdapter(delegateAdapter: ViewTypeDelegateAdapter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private var listener: OnReportClickListener? = null
    private val loadingItem = object : ViewType {
        override fun getViewType() = VIEW_TYPE_LOADING
    }

    interface OnReportClickListener {
        fun onReportClick(report: BaseReport)
    }
    
    init {
        delegateAdapters.put(VIEW_TYPE_LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(VIEW_TYPE_ITEM, delegateAdapter)
        items = ArrayList()
    }

    fun setOnReportClickListener(listener: OnReportClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int) = items[position].getViewType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position], listener)
    }

    fun addLoading() {
        if (items.isEmpty() || getItemViewType(items.lastIndex) == VIEW_TYPE_LOADING) return
        items.add(loadingItem)
        notifyItemInserted(items.lastIndex)
    }

    fun hideLoading() {
        if (items.isEmpty() || getItemViewType(items.lastIndex) == VIEW_TYPE_ITEM) return
        items.removeAt(items.lastIndex)
        notifyItemRemoved(items.size)
    }

    fun setReports(reports: List<ViewType>) {
        items.clear()
        items.addAll(reports)
        notifyDataSetChanged()
    }

    fun addReports(reports: List<ViewType>) {
        hideLoading()
        val initPosition = items.size + 1
        items.addAll(reports)
        notifyItemRangeInserted(initPosition, items.size)
    }
}