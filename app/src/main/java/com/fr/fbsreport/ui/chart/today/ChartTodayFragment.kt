package com.fr.fbsreport.ui.chart.today

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.model.SaleReport
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.network.ReportResponse
import com.fr.fbsreport.utils.formatWithDot
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_chart_today.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

class ChartTodayFragment : BaseFragment() {

    private val branchCode: String by androidLazy {
        arguments?.getString(EXTRA_BRANCH_CODE) ?: ""
    }
    private var total = 0L

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = ChartTodayFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_chart_today
    }

    override fun initViews() {
        line_chart.apply {
            val dataDate = ArrayList<String>()
            dataDate.add("")
            dataDate.add("4am")
            dataDate.add("8am")
            dataDate.add("12pm")
            dataDate.add("4pm")
            dataDate.add("8pm")
            dataDate.add("")
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
                dataDate[value.toInt()] // xVal is a string array
            }
        }
        getBaseActivity()?.showLoading()
        requestData()
    }

    private fun requestData() {
        launch(UI) {
            try {
                val response = fetchData()
                getBaseActivity()?.hideLoading()
                fillChart(response.data)
                txt_total.text = total.formatWithDot()
            } catch (e: Throwable) {
                getBaseActivity()?.hideLoading()
                context?.let { ErrorUtils.handleCommonError(it, e) }
            }
        }
    }

    suspend fun fetchData(): ReportResponse<SaleReport> {
        return suspendCoroutine { continuation ->
            requestApi(appRepository.getSaleReport(branchCode, null, null, 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ reportResponse ->
                        continuation.resume(reportResponse)
                    }, { err ->
                        continuation.resumeWithException(err)
                    }))
        }
    }

    private fun fillChart(data: ArrayList<SaleReport>) {
        val values = ArrayList<Entry>()
        for (i in 0..6) {
            values.add(Entry(i.toFloat(), data[i].total.toFloat()))
            total += data[i].total
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
}