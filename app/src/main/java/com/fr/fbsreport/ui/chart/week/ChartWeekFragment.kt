package com.fr.fbsreport.ui.chart.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_chart_week.*


class ChartWeekFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = ChartWeekFragment().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart_week, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val values = ArrayList<Entry>()
        values.add(Entry(0f, 8f))
        values.add(Entry(1f, 12f))
        values.add(Entry(2f, 4f))
        values.add(Entry(3f, 7f))
        values.add(Entry(4f, 20f))
        values.add(Entry(5f, 22f))
        values.add(Entry(6f, 12f))

        val dataDate = ArrayList<String>()
        dataDate.add("Sun")
        dataDate.add("Mon")
        dataDate.add("Tue")
        dataDate.add("Wed")
        dataDate.add("Thu")
        dataDate.add("Fri")
        dataDate.add("Sat")

        val lineDataSet = LineDataSet(values, "Data set 1")
        lineDataSet.color = resources.getColor(R.color.orange)
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)
        val lineData = LineData(dataSets)
        lineData.setDrawValues(false)
        line_chart.legend.isEnabled = false
        line_chart.description.text = ""
        line_chart.axisRight.isEnabled = false
        line_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        line_chart.axisLeft.axisMinimum = 0f
        line_chart.xAxis.setValueFormatter(object : IAxisValueFormatter {

            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return dataDate[value.toInt()] // xVal is a string array
            }
        })
        line_chart.data = lineData
    }
}