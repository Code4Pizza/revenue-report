package com.fr.fbsreport.ui.report.bill

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import com.fr.fbsreport.R
import com.fr.fbsreport.base.*
import com.fr.fbsreport.network.Chart
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.network.Section
import com.fr.fbsreport.ui.report.BaseReportChartFragment
import com.fr.fbsreport.utils.formatWithDot
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_base_report_chart.*
import kotlinx.android.synthetic.main.item_view_section.view.*
import kotlinx.android.synthetic.main.view_bill_section_1.*
import kotlinx.android.synthetic.main.view_bill_section_2.*
import kotlinx.android.synthetic.main.view_report_chart.*
import java.util.*
import kotlin.collections.ArrayList

class BillReportChartFragment : BaseReportChartFragment() {

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String, date: String) = BillReportChartFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            bundle.putString(EXTRA_FILTER_DATE, date)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_report_chart
    }

    override fun getTextIdToolbarLeft(): Int? {
        return R.string.action_back
    }

    override fun onItemLeft() {
        super.onItemLeft()
        getBaseBottomTabActivity()?.onBackPressed()
    }

    override fun initChart() {
        line_chart.apply {
            xAxisValues = when (date) {
                FILTER_TYPE_TODAY, FILTER_TYPE_YESTERDAY -> resources.getStringArray(R.array.axis_date)
                FILTER_TYPE_WEEK -> resources.getStringArray(R.array.axis_week)
                FILTER_TYPE_MONTH -> resources.getStringArray(R.array.axis_month)
                else -> throw Throwable("Unavailable date")
            }
            legend.isEnabled = false
            description.text = ""
            setNoDataText("")

            isDragEnabled = false
            setScaleEnabled(false)
            isScaleXEnabled = false
            isScaleYEnabled = false

            axisRight.isEnabled = false

            axisLeft.axisMinimum = 0f
            axisLeft.valueFormatter = IAxisValueFormatter { value, axis ->
                val yValue: String
                if (value > 1000000) yValue = String.format("%s tr", (value / 1000000).toInt().toString())
                else if (value > 100000) yValue = String.format("%s tr", (value / 100).toInt().toString())
                else yValue = value.toInt().toString()
                yValue
            }

            xAxis.setLabelCount(14, true)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = IAxisValueFormatter { value, _ ->
                xAxisValues[value.toInt()] // xVal is a string array
            }

        }
    }

    override fun requestData() {
        requestApi(appRepository.getDashboard(branchCode, "bill", date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    //      getBaseActivity()?.showLoading()
                    view_data.visibility = View.GONE
                }
                .subscribe({ response ->
                    getBaseActivity()?.hideLoading()
                    view_data.visibility = View.VISIBLE
                    fillChart(response.data.charts)
                    showSections(response.data.sections)
                }, { err ->
                    getBaseActivity()?.hideLoading()
                    context?.let { ErrorUtils.handleCommonError(it, err) }
                }))
    }

    override fun fillChart(charts: ArrayList<Chart>) {
        var totalSale: Long = 0
        for (i in 0 until charts.size) {
            val chart = charts[i]
            chartMap.put(chart.time.toInt(), chart.total.toFloat())
        }
        val values = ArrayList<Entry>()
        for (i in 0 until chartMap.size()) {
            val key = chartMap.keyAt(i)
            totalSale += chartMap[key].toLong()
            values.add(Entry(key.toFloat(), chartMap[key].toFloat()))
        }
        val lineDataSet = LineDataSet(values, "")
        context?.let {
            lineDataSet.color = ContextCompat.getColor(it, R.color.orange)
            lineDataSet.setDrawCircles(false)
            lineDataSet.setDrawCircleHole(true)
            lineDataSet.mode = LineDataSet.Mode.LINEAR
        }
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)
        val lineData = LineData(dataSets)
        lineData.setDrawValues(false)

        line_chart.run {
            clear()
            data = lineData
            invalidate()
        }

        txt_total.text = totalSale.formatWithDot()
        txt_nb_order.text = getString(R.string.view_chart_report_number_orders, Random().nextInt(100))
    }

    private fun showSections(sections: ArrayList<Section?>) {
        sections[0]?.apply {
            ll_section_1.removeAllViews()
            txt_name_section_1.text = name
            for (report in reports) {
                val sectionView = LayoutInflater.from(context).inflate(R.layout.item_view_section, ll_section_1, false)
                sectionView.txt_name.text = report.title
                sectionView.txt_sale.text = report.total.toLong().formatWithDot()
                ll_section_1.addView(sectionView)
            }
            view_underline_section_1.visibility = if (reports.isEmpty()) View.GONE else View.VISIBLE
            txt_view_report_1.setOnClickListener { getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportFragment.newInstance(branchCode)) }
        }
        sections[1]?.apply {
            ll_section_2.removeAllViews()
            txt_name_section_2.text = name
            for (report in reports) {
                val sectionView = LayoutInflater.from(context).inflate(R.layout.item_view_section, ll_section_2, false)
                sectionView.txt_name.text = report.title
                sectionView.txt_sale.text = report.total.toLong().formatWithDot()
                ll_section_2.addView(sectionView)
            }
            view_underline_section_2.visibility = if (reports.isEmpty()) View.GONE else View.VISIBLE
            txt_view_report_1.setOnClickListener { getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportFragment.newInstance(branchCode)) }
        }
    }
}