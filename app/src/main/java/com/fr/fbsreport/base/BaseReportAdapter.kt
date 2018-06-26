package com.fr.fbsreport.base

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.model.BaseReport

open class BaseReportAdapter<T : ViewType>(delegateAdapter: ViewTypeDelegateAdapter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var reports: ArrayList<T>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private var listener: OnReportClickListener? = null

    interface OnReportClickListener {
        fun onReportClick(report: BaseReport)
    }

    init {
        delegateAdapters.put(VIEW_TYPE_ITEM, delegateAdapter)
        reports = ArrayList()
    }

    fun setOnReportClickListener(listener: OnReportClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return reports.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, reports[position], listener)
    }

    override fun getItemViewType(position: Int) = reports[position].getViewType()

    fun clear() {
        reports.clear()
        notifyDataSetChanged()
    }

    fun addReports(reports: ArrayList<T>) {
        val initPosition = this.reports.size + 1
        this.reports.addAll(reports)
        notifyItemRangeInserted(initPosition, this.reports.size)
    }

    fun getReports(): List<T> =
            reports
                    .filter { it.getViewType() == VIEW_TYPE_ITEM }
                    .map { it }
}