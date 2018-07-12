package com.fr.fbsreport.ui.report.bill

import android.app.DatePickerDialog
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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_base_report_custom.*
import kotlinx.android.synthetic.main.item_view_section_bill.view.*
import kotlinx.android.synthetic.main.view_bill_section_1.*
import kotlinx.android.synthetic.main.view_bill_section_2.*
import kotlinx.android.synthetic.main.view_bill_section_3.*
import kotlinx.android.synthetic.main.view_bill_section_4.*
import kotlinx.android.synthetic.main.view_bill_section_5.*
import kotlinx.android.synthetic.main.view_report_chart.*
import java.util.*
import java.util.concurrent.TimeUnit

class BillReportCustomFragment : BaseReportChartFragment() {

    private var startDate: String? = null
    private var endDate: String? = null

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String, date: String) = BillReportCustomFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            bundle.putString(EXTRA_FILTER_DATE, date)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_report_custom
    }

    override fun initViews() {
        txt_start_date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                startDay = dayOfMonth
                startMonth = monthOfYear
                startYear = year
                txt_start_date.text = String.format("%s/%s/%s", dayOfMonth.toString(), (monthOfYear + 1).toString(), year.toString())
                startDate = String.format("%s-%s-%s", year.toString(), (monthOfYear + 1).toString(), dayOfMonth.toString())
                if (endDate != null) requestData()
            }, startYear ?: currentYear(), startMonth ?: currentMonth(), startDay ?: currentDate())
            datePickerDialog.show()
        }
        txt_end_date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                endDay = dayOfMonth
                endMonth = monthOfYear
                endYear = year
                txt_end_date.text = String.format("%s/%s/%s", dayOfMonth.toString(), (monthOfYear + 1).toString(), year.toString())
                endDate = String.format("%s-%s-%s", year.toString(), (monthOfYear + 1).toString(), dayOfMonth.toString())
                if (startDate != null) requestData()
            }, endYear ?: currentYear(), endMonth ?: currentMonth(), endDay ?: currentDate())
            datePickerDialog.show()
        }
        initChart()
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

            xAxis.setLabelCount(2, true)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = IAxisValueFormatter { value, _ ->
                when (value.toInt()) {
                    0 -> startDate.formatDate()
                    chartHashMap.size - 1 -> endDate.formatDate()
                    else -> ""
                }
            }
        }
    }

    override fun requestData() {
        requestApi(appRepository.getDashboard(branchCode, "bill", FILTER_TYPE_CUSTOM, startDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    showLoadingRequest()
                }
                .doAfterTerminate {
                    getBaseActivity()?.hideLoading()
                }
                .subscribe({ data ->
                    hideLoadingSuccess()
                    fillChart(data)
                    showSections(data.sections)
                }, { err ->
                    hideLoadingFailed()
                    context?.let { ErrorUtils.handleCommonError(it, err) }
                }))
    }

    private fun showLoadingRequest() {
        view_data.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
        txt_no_data.visibility = View.GONE
    }

    private fun hideLoadingSuccess() {
        view_data.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
        txt_no_data.visibility = View.GONE
    }

    private fun hideLoadingFailed() {
        view_data.visibility = View.GONE
        progress_bar.visibility = View.GONE
        txt_no_data.visibility = View.VISIBLE
    }

    override fun fillChart(data: Dashboard) {
        chartHashMap.clear()
        val daysBetweenDates = TimeUnit.DAYS.convert(endDate.toDate() - startDate.toDate(), TimeUnit.MILLISECONDS)
        for (i in 0 until daysBetweenDates.toInt()) {
            chartHashMap[i] = 0f
        }
        for (i in 0 until data.charts.size) {
            chartHashMap[i] = data.charts[i].total.toFloat()
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
            txt_view_report_1.setOnClickListener { getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportFragment.newInstance(branchCode)) }
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
            txt_view_report_2.setOnClickListener { getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportFragment.newInstance(branchCode)) }
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