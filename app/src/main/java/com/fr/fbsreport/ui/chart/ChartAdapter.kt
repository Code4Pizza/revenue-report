package com.fr.fbsreport.ui.chart

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseRecyclerAdapter
import com.fr.fbsreport.base.VIEW_TYPE_CHART
import com.fr.fbsreport.base.VIEW_TYPE_CHART_CATEGORY
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.extension.inflate
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.item_view_chart.view.*
import kotlinx.android.synthetic.main.item_view_chart_category.view.*

class ChartAdapter : BaseRecyclerAdapter<ViewType, RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CHART -> ChartViewHolder(parent.inflate(R.layout.item_view_chart))
            VIEW_TYPE_CHART_CATEGORY -> CategoryViewHolder(parent.inflate(R.layout.item_view_chart_category))
            else -> throw RuntimeException("View holder not available")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is ChartViewHolder) {
            holder.bind()
        }
        if (holder is CategoryViewHolder) {
            holder.bind()
        }
    }

    class ChartViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var values = ArrayList<Entry>()
        private var lineData: LineData

        init {
            values.add(Entry(0f, 10f))
            values.add(Entry(5f, 15f))
            values.add(Entry(10f, 12f))
            values.add(Entry(15f, 18f))
            values.add(Entry(20f, 20f))
            values.add(Entry(25f, 25f))
            values.add(Entry(30f, 30f))

            val lineDataSet = LineDataSet(values, "Data set 1")
            lineDataSet.color = itemView?.context?.resources?.getColor(R.color.orange)!!
            lineDataSet.setDrawCircles(false)
            lineDataSet.setDrawCircleHole(false)
            lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(lineDataSet)
            lineData = LineData(dataSets)
        }


        fun bind() {
            itemView.line_chart.clear()
            itemView.line_chart.legend.isEnabled = false
            itemView.line_chart.description.text = ""
            itemView.line_chart.axisRight.isEnabled = false
            itemView.line_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            itemView.line_chart.xAxis.valueFormatter = IAxisValueFormatter { value, _ ->
                if (value == 0f) return@IAxisValueFormatter ""
                value.toString()
            }
            itemView.line_chart.axisLeft.axisMinimum = 0f
            itemView.line_chart.data = lineData
        }
    }

    class CategoryViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            val inflater = LayoutInflater.from(itemView?.context)

            itemView.ll_category_items.removeAllViews()
            for (i in 1..3) {
                itemView.ll_category_items.addView(inflater.inflate(R.layout.item_view_chart_category_item, null, false))
            }
        }
    }
}