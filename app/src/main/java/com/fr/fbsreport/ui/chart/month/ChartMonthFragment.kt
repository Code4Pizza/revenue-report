package com.fr.fbsreport.ui.chart.month

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.model.Chart
import com.fr.fbsreport.model.ChartCategory
import com.fr.fbsreport.ui.chart.ChartAdapter
import kotlinx.android.synthetic.main.fragment_chart_month.*

class ChartMonthFragment : BaseFragment() {

    private lateinit var chartAdapter: ChartAdapter

    companion object {
        @JvmStatic
        fun newInstance() = ChartMonthFragment().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chartAdapter = ChartAdapter(context!!)

        recycler_chart.layoutManager = LinearLayoutManager(context)
        recycler_chart.adapter = chartAdapter

        val items = ArrayList<ViewType>()
        items.add(Chart())
        items.add(ChartCategory())
        items.add(ChartCategory())

        chartAdapter.setItems(items)

//        val values = ArrayList<Entry>()
//        values.add(Entry(0f, 10f))
//        values.add(Entry(5f, 15f))
//        values.add(Entry(10f, 12f))
//        values.add(Entry(15f, 18f))
//        values.add(Entry(20f, 20f))
//        values.add(Entry(25f, 25f))
//        values.add(Entry(30f, 30f))
//
//        val lineDataSet = LineDataSet(values, "Data set 1")
//        lineDataSet.color = resources.getColor(R.color.orange)
//        lineDataSet.setDrawCircles(false)
//        lineDataSet.setDrawCircleHole(false)
//        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//        val dataSets = ArrayList<ILineDataSet>()
//        dataSets.add(lineDataSet)
//        val lineData = LineData(dataSets)
//        lineData.setDrawValues(false)
//        line_chart.legend.isEnabled = false
//        line_chart.description.text = ""
//        line_chart.axisRight.isEnabled = false
//        line_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
//
//        line_chart.xAxis.setValueFormatter(object : IAxisValueFormatter {
//
//            override fun getFormattedValue(value: Float, axis: AxisBase): String {
//                if (value == 0f) return ""
//                return value.toString()
//            }
//        })
//
//        line_chart.axisLeft.axisMinimum = 0f
//        line_chart.data = lineData
    }
}