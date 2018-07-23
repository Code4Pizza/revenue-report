package com.fr.fbsreport.ui.analysis

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.VIEW_TEST_1
import com.fr.fbsreport.extension.VIEW_TEST_2
import com.fr.fbsreport.source.network.Dashboard

class AnalysisAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private val delegates = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegates.put(VIEW_TEST_1, ChartDelegateAdapter())
        delegates.put(VIEW_TEST_2, SectionDelegateAdapter())
        items = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = delegates.get(viewType).onCreateViewHolder(parent)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates.get(getItemViewType(position)).onBindViewHolder(holder, items[position], null)
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()
    
    fun setData(dashboard: Dashboard){
        items.clear()
        items.add(dashboard)
        for (i in 0 until dashboard.sections.size) {
            dashboard.sections[i]?.let { items.add(it) }
        }
        notifyDataSetChanged()
    }

}