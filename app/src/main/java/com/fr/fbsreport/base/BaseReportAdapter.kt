package com.fr.fbsreport.base

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

open class BaseReportAdapter<T : ViewType>(delegateAdapter: ViewTypeDelegateAdapter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var items: ArrayList<T>
    protected var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.put(VIEW_TYPE_ITEM, delegateAdapter)
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

    fun addReports(reports: ArrayList<T>) {
        val initPosition = items.size + 1
        items.addAll(reports)
        notifyItemRangeInserted(initPosition, items.size)
    }

    fun getReports(): List<T> =
            items
                    .filter { it.getViewType() == VIEW_TYPE_ITEM }
                    .map { it }
}