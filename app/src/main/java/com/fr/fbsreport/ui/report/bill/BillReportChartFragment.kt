package com.fr.fbsreport.ui.report.bill

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import com.fr.fbsreport.R
import com.fr.fbsreport.extension.*
import com.fr.fbsreport.source.network.Dashboard
import com.fr.fbsreport.source.network.ErrorUtils
import com.fr.fbsreport.source.network.Section
import com.fr.fbsreport.ui.home.HomeActivity
import com.fr.fbsreport.ui.report.BaseReportChartFragment
import com.fr.fbsreport.ui.report.delete.DeleteReportFragment
import com.fr.fbsreport.ui.report.discount.DiscountReportFragment
import com.fr.fbsreport.ui.report.item.ItemReportFragment
import com.fr.fbsreport.ui.report.revenue.RevenueReportFragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_base_report_chart.*
import kotlinx.android.synthetic.main.item_view_section_bill.view.*
import kotlinx.android.synthetic.main.view_bill_section_1.*
import kotlinx.android.synthetic.main.view_bill_section_2.*
import kotlinx.android.synthetic.main.view_bill_section_3.*
import kotlinx.android.synthetic.main.view_bill_section_4.*
import kotlinx.android.synthetic.main.view_bill_section_5.*
import kotlinx.android.synthetic.main.view_report_chart.*

class BillReportChartFragment : BaseReportChartFragment() {

    var firstLoading: Boolean = true

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String, date: String) = BillReportChartFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            bundle.putString(EXTRA_FILTER_DATE, date)
            arguments = bundle
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed && firstLoading) {
            getBaseActivity()?.showLoading()
            requestData()
        }
    }

    override fun initViews() {
        super.initViews()
        swipe_refresh.setOnRefreshListener {
            requestData()
        }
        view_data.visibility = View.GONE
        if (filter == FILTER_TYPE_TODAY) {
            getBaseActivity()?.showLoading()
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
                    value > 1000000 -> String.format("%str", (value / 1000000).toInt().toString())
                    value > 1000 -> String.format("%sk", (value / 100).toInt().toString())
                    else -> value.toInt().toString()
                }
            }

            xAxis.setLabelCount(when (filter) {
                FILTER_TYPE_YESTERDAY, FILTER_TYPE_TODAY -> 9
                FILTER_TYPE_WEEK -> 7
                FILTER_TYPE_MONTH -> 12
                else -> 2
            }, true)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = IAxisValueFormatter { value, _ ->
                when (filter) {
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
    }

    override fun requestData() {
        requestApi(appRepository.getDashboard(branchCode, "bill", filter, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (!swipe_refresh.isRefreshing) view_data.visibility = View.GONE
                }
                .doAfterTerminate {
                    getBaseActivity()?.hideLoading()
                    swipe_refresh.isRefreshing = false
                }
                .subscribe({ data ->
                    firstLoading = false
                    view_data.visibility = View.VISIBLE
                    fillChart(data)
                    showSections(data.sections)
                }, { err ->
                    context?.let {
                        ErrorUtils.handleCommonError(it, err)
                    }
                }))
    }

    override fun fillChart(data: Dashboard) {
        when (filter) {
            FILTER_TYPE_TODAY, FILTER_TYPE_YESTERDAY -> {
                for (i in 0 until data.charts.size) {
                    val chart = data.charts[i]
                    chartHashMap[chart.time.toInt()] = chart.total.toFloat()
                }
            }
            FILTER_TYPE_WEEK -> {
                for (i in 0 until data.charts.size) {
                    val chart = data.charts[i]
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
                for (i in 0 until data.charts.size) {
                    val chart = data.charts[i]
                    val dayOfMonth = chart.time.getDayOfMonth()
                    chartHashMap[dayOfMonth] = chart.total.toFloat()
                }
            }
        }
        val values = ArrayList<Entry>()
        for (entry in chartHashMap.entries) {
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
            if (data.totalMoney != 0L) this.data = lineData
            invalidate()
        }

        txt_total.text = data.totalMoney.formatWithDot()
        txt_nb_order.text = getString(R.string.view_chart_report_number_orders, data.totalBill)
    }

    private fun showSections(sections: ArrayList<Section?>) {
        sections[0]?.apply {
            ll_section_1.removeAllViews()
            txt_name_section_1.text = name
            for (report in reports) {
                val sectionView = LayoutInflater.from(context).inflate(R.layout.item_view_section_bill, ll_section_1, false)
                sectionView.txt_name.text = report.title
                sectionView.txt_sale.text = report.total.toLong().formatWithDot()
                ll_section_1.addView(sectionView)
            }
            view_underline_section_1.visibility = if (reports.isEmpty()) View.GONE else View.VISIBLE
            txt_view_report_1.setOnClickListener { (getBaseBottomTabActivity() as HomeActivity).switchTab(INDEX_REPORT, RevenueReportFragment.newInstance(branchCode)) }
        }
        sections[1]?.apply {
            ll_section_2.removeAllViews()
            txt_name_section_2.text = name
            for (report in reports) {
                val sectionView = LayoutInflater.from(context).inflate(R.layout.item_view_section_bill, ll_section_2, false)
                sectionView.txt_name.text = report.title
                sectionView.txt_sale.text = report.total.toLong().formatWithDot()
                ll_section_2.addView(sectionView)
            }
            view_underline_section_2.visibility = if (reports.isEmpty()) View.GONE else View.VISIBLE
            txt_view_report_2.setOnClickListener { (getBaseBottomTabActivity() as HomeActivity).switchTab(INDEX_REPORT, BillReportFragment.newInstance(branchCode)) }
        }
        sections[2]?.apply {
            ll_section_3.removeAllViews()
            txt_name_section_3.text = name
            for (report in reports) {
                val sectionView = LayoutInflater.from(context).inflate(R.layout.item_view_section_bill, ll_section_3, false)
                if (type == "item") {
                    sectionView.txt_name.text = report.name
                    sectionView.txt_sale.text = report.total.toLong().formatWithDot()
                }
                ll_section_3.addView(sectionView)
            }
            view_underline_section_3.visibility = if (reports.isEmpty()) View.GONE else View.VISIBLE
            txt_view_report_3.setOnClickListener { (getBaseBottomTabActivity() as HomeActivity).switchTab(INDEX_REPORT, ItemReportFragment.newInstance(branchCode)) }
        }
        sections[3]?.apply {
            ll_section_4.removeAllViews()
            txt_name_section_4.text = name
            for (report in reports) {
                val sectionView = LayoutInflater.from(context).inflate(R.layout.item_view_section_bill, ll_section_4, false)
                sectionView.txt_name.text = report.title
                sectionView.txt_sale.text = report.total.toLong().formatWithDot()
                ll_section_4.addView(sectionView)
            }
            view_underline_section_4.visibility = if (reports.isEmpty()) View.GONE else View.VISIBLE
            txt_view_report_4.setOnClickListener { (getBaseBottomTabActivity() as HomeActivity).switchTab(INDEX_REPORT, DeleteReportFragment.newInstance(branchCode)) }
        }
        sections[4]?.apply {
            ll_section_5.removeAllViews()
            txt_name_section_5.text = name
            for (report in reports) {
                val sectionView = LayoutInflater.from(context).inflate(R.layout.item_view_section_bill, ll_section_5, false)
                sectionView.txt_name.text = report.title
                sectionView.txt_sale.text = report.total.toLong().formatWithDot()
                ll_section_5.addView(sectionView)
            }
            view_underline_section_5.visibility = if (reports.isEmpty()) View.GONE else View.VISIBLE
            txt_view_report_5.setOnClickListener { (getBaseBottomTabActivity() as HomeActivity).switchTab(INDEX_REPORT, DiscountReportFragment.newInstance(branchCode)) }
        }
    }
}