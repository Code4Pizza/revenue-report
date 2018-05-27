package com.fr.fbsreport.ui.chart.yesterday

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
import kotlinx.android.synthetic.main.fragment_chart_yesterday.*

class ChartYesterdayFragment : BaseFragment() {
    companion object {
        fun newInstance() = ChartYesterdayFragment().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart_yesterday, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val values = ArrayList<Entry>()
        values.add(Entry(0f, 2f))
        values.add(Entry(1f, 2f))
        values.add(Entry(2f, 6f))
        values.add(Entry(3f, 12f))
        values.add(Entry(4f, 5f))
        values.add(Entry(5f, 4f))
        values.add(Entry(6f, 1f))

        val dataDate = ArrayList<String>()
        dataDate.add("")
        dataDate.add("4am")
        dataDate.add("8am")
        dataDate.add("12pm")
        dataDate.add("4pm")
        dataDate.add("8pm")
        dataDate.add("")

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