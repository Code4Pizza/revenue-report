package com.fr.fbsreport.ui.home.report.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.inflate

class DeleteReportTitleDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = DeleteReportTitleViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class DeleteReportTitleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_view_reject_report_title)
    )
}