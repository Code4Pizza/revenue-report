package com.fr.fbsreport.ui.report.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.fr.fbsreport.R
import com.fr.fbsreport.base.*
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.network.Section
import com.fr.fbsreport.ui.report.BaseReportChartFragment
import com.fr.fbsreport.utils.formatWithDot
import com.fr.fbsreport.widget.FilterTimeDialog
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
        fun newInstance(branchCode: String) = BillReportChartFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_report_chart
    }

    override fun initChart() {
        line_chart.apply {
            xAxisValues = resources.getStringArray(R.array.axis_date)
            line_chart.legend.isEnabled = false
            line_chart.description.text = ""
            line_chart.setNoDataText("")
            line_chart.axisRight.isEnabled = false
            line_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            line_chart.axisLeft.axisMinimum = 0f
            line_chart.isDragEnabled = false
            line_chart.setScaleEnabled(false)
            line_chart.isScaleXEnabled = false
            line_chart.isScaleYEnabled = false
            line_chart.xAxis.valueFormatter = IAxisValueFormatter { value, _ ->
                xAxisValues[value.toInt()] // xVal is a string array
            }
        }
    }

    override fun initFilter() {
        txt_filter.setText(R.string.filter_time_dialog_today)
        txt_filter.setOnClickListener {
            val filterDialog = FilterTimeDialog.newInstance(date, object : FilterTimeDialog.OnClickFilterDialogListener {
                override fun onClickFilter(time: String) {
                    // Assign new time filter to request data
                    date = time
                    when (date) {
                        FILTER_TYPE_TODAY -> txt_filter.setText(R.string.filter_time_dialog_today)
                        FILTER_TYPE_YESTERDAY -> txt_filter.setText(R.string.filter_time_dialog_yesterday)
                        FILTER_TYPE_WEEK -> txt_filter.setText(R.string.filter_time_dialog_week)
                        FILTER_TYPE_MONTH -> txt_filter.setText(R.string.filter_time_dialog_month)
                    }
                    requestData()
                }
            })
            getBaseActivity()?.showDialogFragment(filterDialog)
        }
    }

    override fun initViews() {
        super.initViews()
        txt_detail.setOnClickListener {
            getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportFragment.newInstance(branchCode))
        }
    }

    override fun requestData() {
        requestApi(appRepository.getDashboard(branchCode, "bill", date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    getBaseActivity()?.showLoading()
                    view_data.visibility = View.GONE
                }
                .subscribe({ response ->
                    getBaseActivity()?.hideLoading()
                    view_data.visibility = View.VISIBLE
                    fillChart()
                    showSections(response.data.sections)
                }, { err ->
                    getBaseActivity()?.hideLoading()
                    context?.let { ErrorUtils.handleCommonError(it, err) }
                }))
    }

    override fun fillChart() {
        line_chart.clear()
        val values = ArrayList<Entry>()
        for (i in 0..6) {
            values.add(Entry(i.toFloat(), Random().nextInt(50).toFloat()))
        }
        val lineDataSet = LineDataSet(values, "DataResponse set 1")
        lineDataSet.color = resources.getColor(R.color.orange)
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)
        val lineData = LineData(dataSets)
        lineData.setDrawValues(false)
        line_chart.data = lineData
        line_chart.invalidate()
    }

    private fun showSections(sections: ArrayList<Section?>) {
        sections[0]?.run {
            ll_section_1.removeAllViews()
            txt_name_section_1.text = name
            for (report in reports) {
                val sectionView = LayoutInflater.from(context).inflate(R.layout.item_view_section, ll_section_1, false)
                sectionView.txt_name.text = report.title
                sectionView.txt_sale.text = report.total.toLong().formatWithDot()
                ll_section_1.addView(sectionView)
            }
        }
        sections[1]?.run {
            ll_section_2.removeAllViews()
            txt_name_section_2.text = name
            for (report in reports) {
                val sectionView = LayoutInflater.from(context).inflate(R.layout.item_view_section, ll_section_2, false)
                sectionView.txt_name.text = report.title
                sectionView.txt_sale.text = report.total.toLong().formatWithDot()
                ll_section_2.addView(sectionView)
            }
        }
    }
}