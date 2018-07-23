package com.fr.fbsreport.ui.analysis

import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.ViewTypeDelegateAdapter
import com.fr.fbsreport.extension.*
import com.fr.fbsreport.source.network.Dashboard
import com.fr.fbsreport.ui.report.BaseReportAdapter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.view_test_chart.view.*
import java.util.*

class ChartDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ChartHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, listener: BaseReportAdapter.OnReportClickListener?) {
        (holder as ChartHolder).bind(item as Dashboard)
    }

    class ChartHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.view_test_chart)) {

        private val chartHashMap: LinkedHashMap<Int, Float> by androidLazy {
            val chartMap = LinkedHashMap<Int, Float>()
            for (i in 1..Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)) {
                chartMap[i] = 0f
            }
            chartMap
        }

        init {
            itemView.line_chart.apply {
                legend.isEnabled = false
                description.text = ""
                setNoDataText(context.getString(R.string.text_no_data))
                setNoDataTextTypeface(ResourcesCompat.getFont(context, R.font.sf_regular))
                setNoDataTextColor(context.color(R.color.orange))

                setScaleEnabled(false)
                isDragEnabled = false
                isScaleXEnabled = false
                isScaleYEnabled = false

                axisRight.isEnabled = false

                axisLeft.axisMinimum = 0f
                axisLeft.valueFormatter = IAxisValueFormatter { value, _ ->
                    when {
                        value > 1000000 -> String.format("%str", (value / 1000000).toInt().toString())
                        value > 1000 -> String.format("%sk", (value / 100).toInt().toString())
                        else -> value.toInt().toString()
                    }
                }

                xAxis.setLabelCount(12)
                xAxis.setDrawGridLines(false)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IAxisValueFormatter { value, _ ->
                    Math.round(value).toString()
                }
            }
        }

        fun bind(dashboard: Dashboard) {
            for (i in 0 until dashboard.charts.size) {
                val chart = dashboard.charts[i]
                val dayOfMonth = chart.time.getDayOfMonth()
                chartHashMap[dayOfMonth] = chart.total.toFloat()
            }

            val values = ArrayList<Entry>()
            for (entry in chartHashMap.entries) {
                values.add(Entry(entry.key.toFloat(), entry.value))
            }

            val lineDataSet = LineDataSet(values, "")
            lineDataSet.apply {
                color = itemView.context.color(R.color.orange)!!
                setDrawCircles(false)
                setDrawCircleHole(false)
                mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            }

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(lineDataSet)
            val lineData = LineData(dataSets)
            lineData.setDrawValues(false)

            itemView.line_chart.run {
                clear()
                if (dashboard.totalMoney != 0L) this.data = lineData
                invalidate()
            }

            itemView.txt_total.text = dashboard.totalMoney.formatWithDot()
            itemView.txt_nb_order.text = itemView.context.getString(R.string.view_chart_report_number_orders, dashboard.totalBill)
        }
    }
}