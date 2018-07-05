package com.fr.fbsreport.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.ui.report.BaseReportAdapter

interface ViewTypeDelegateAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, listener: BaseReportAdapter.OnReportClickListener?)

}