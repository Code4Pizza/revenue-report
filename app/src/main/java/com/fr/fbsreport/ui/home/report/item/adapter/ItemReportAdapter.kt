package com.fr.fbsreport.ui.home.report.item.adapter

import android.support.v7.widget.RecyclerView
import com.fr.fbsreport.base.BaseReportAdapter
import com.fr.fbsreport.model.ItemReport

class ItemReportAdapter(itemReportDelegateAdapter: ItemReportDelegateAdapter) : BaseReportAdapter<ItemReport>(itemReportDelegateAdapter) {

    private lateinit var listener: OnItemReportAdapterClickListener

    interface OnItemReportAdapterClickListener {
        fun onClickItemReport(itemReport: ItemReport)
    }

    fun setOnItemReportAdapterClickListener(listener: OnItemReportAdapterClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (delegateAdapters.get(getItemViewType(position)) is ItemReportDelegateAdapter) {
            (delegateAdapters.get(getItemViewType(position)) as ItemReportDelegateAdapter).setOnItemReportAdapterClickListener(listener)
        }
    }

}