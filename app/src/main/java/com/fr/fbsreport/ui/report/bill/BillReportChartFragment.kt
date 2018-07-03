package com.fr.fbsreport.ui.report.bill

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import com.fr.fbsreport.R
import com.fr.fbsreport.base.*
import com.fr.fbsreport.extension.color
import com.fr.fbsreport.extension.formatWithDot
import com.fr.fbsreport.extension.getDayOfMonth
import com.fr.fbsreport.extension.getDayOfWeek
import com.fr.fbsreport.network.Chart
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.network.Section
import com.fr.fbsreport.ui.report.BaseReportChartFragment
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

    override fun initViews() {
        super.initViews()
        swipe_refresh.setOnRefreshListener {
            requestData()
        }
    }

    override fun initChart() {
        line_chart.apply {
            legend.isEnabled = false
            description.text = ""
            setNoDataText(getString(R.string.text_no_data))
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
                    value > 1000000 -> String.format("%s tr", (value / 1000000).toInt().toString())
                    value > 100000 -> String.format("%s tr", (value / 100).toInt().toString())
                    else -> value.toInt().toString()
                }
            }

            xAxis.setLabelCount(when (date) {
                FILTER_TYPE_YESTERDAY, FILTER_TYPE_TODAY -> 9
                FILTER_TYPE_WEEK -> 7
                FILTER_TYPE_MONTH -> 12
                else -> 2
            }, true)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = IAxisValueFormatter { value, _ ->
                when (date) {
                    FILTER_TYPE_YESTERDAY, FILTER_TYPE_TODAY -> String.format("%sh", Math.round(value).toString()) // xVal is a string array
                    FILTER_TYPE_WEEK -> {
                        val dayOfWeek = Math.round(value)
                        if (dayOfWeek == 8) "CN"
                        else String.format("T%s", dayOfWeek.toString())
                    }
                    else -> Math.round(value).toString()
                }
            }
        }
        requestData()
    }

    override fun requestData() {
        requestApi(appRepository.getDashboard(branchCode, "bill", date, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view_data.visibility = View.GONE
                }.doFinally {
                    swipe_refresh.isRefreshing = false
                }.subscribe({ response ->
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

        when (date) {
            FILTER_TYPE_TODAY, FILTER_TYPE_YESTERDAY -> {
                for (i in 0 until charts.size) {
                    val chart = charts[i]
                    chartHashMap[chart.time.toInt()] = chart.total.toFloat()
                }
            }
            FILTER_TYPE_WEEK -> {
                for (i in 0 until charts.size) {
                    val chart = charts[i]
                    val dayOfWeek = chart.time.getDayOfWeek()
                    // if return Sunday, set value of index 8 in chart hash map
                    if (dayOfWeek == 1) {
                        chartHashMap[8] = chart.total.toFloat()
                    } else {
                        chartHashMap[dayOfWeek] = chart.total.toFloat()
                    }
                }
            }
            FILTER_TYPE_MONTH -> {
                for (i in 0 until charts.size) {
                    val chart = charts[i]
                    val dayOfMonth = chart.time.getDayOfMonth()
                    chartHashMap[dayOfMonth] = chart.total.toFloat()
                }
            }
        }
        val values = ArrayList<Entry>()
        for (entry in chartHashMap.entries) {
            totalSale += entry.value.toLong()
            values.add(Entry(entry.key.toFloat(), entry.value))
        }

        val lineDataSet = LineDataSet(values, "")
        lineDataSet.apply {
            color = context?.color(R.color.orange)!!
            setDrawCircles(false)
            setDrawCircleHole(false)
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)
        val lineData = LineData(dataSets)
        lineData.setDrawValues(false)

        line_chart.run {
            clear()
            if (totalSale != 0L) data = lineData
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
            txt_view_report_2.setOnClickListener { getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportFragment.newInstance(branchCode)) }
        }
    }
}