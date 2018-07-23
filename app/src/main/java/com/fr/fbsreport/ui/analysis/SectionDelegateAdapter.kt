package com.fr.fbsreport.ui.analysis

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.formatWithDot
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.source.network.Section
import com.fr.fbsreport.ui.report.BaseReportAdapter
import kotlinx.android.synthetic.main.item_view_section_bill.view.*
import kotlinx.android.synthetic.main.view_test_section.view.*

class SectionDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = SectionViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, listener: BaseReportAdapter.OnReportClickListener?) {
        (holder as SectionViewHolder).bind(item as Section)
    }

    class SectionViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.view_test_section)) {

        fun bind(section: Section) {
            itemView.ll_section_1.removeAllViews()
            itemView.txt_name_section_1.text = section.name
            for (report in section.reports) {
                val sectionView = LayoutInflater.from(itemView.context).inflate(R.layout.item_view_section_bill, itemView.ll_section_1, false)
                sectionView.txt_name.text = report.title
                sectionView.txt_sale.text = report.total.formatWithDot()
                itemView.ll_section_1.addView(sectionView)
            }
            itemView.view_underline_section_1.visibility = if (section.reports.isEmpty()) View.GONE else View.VISIBLE
        }
    }
}
