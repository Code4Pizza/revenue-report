package com.fr.fbsreport.ui.chart.week

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.model.ItemReport
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
import kotlinx.android.synthetic.main.fragment_chart_week.*
import kotlin.coroutines.experimental.suspendCoroutine


class ChartWeekFragment : BaseFragment() {

    private val branchCode: String by androidLazy {
        arguments?.getString(EXTRA_BRANCH_CODE) ?: ""
    }
    private var firstLoad = true
    private var total = 0L

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = ChartWeekFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_chart_week
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && firstLoad) {
            getBaseActivity()?.showLoading()
            requestData()
            firstLoad = false
        }
    }

    override fun initViews() {
        line_chart.apply {
            val dataDate = ArrayList<String>()
            dataDate.add("Sun")
            dataDate.add("Mon")
            dataDate.add("Tue")
            dataDate.add("Wed")
            dataDate.add("Thu")
            dataDate.add("Fri")
            dataDate.add("Sat")
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
    }

    private fun requestData() {
//        launch(UI) {
//            try {
//                val response = fetchData()
//                getBaseActivity()?.hideLoading()
//                fillChart(response.data)
//                txt_total.text = total.formatWithDot()
//            } catch (e: Throwable) {
//                getBaseActivity()?.hideLoading()
//                context?.let { ErrorUtils.handleCommonError(it, e) }
//            }
//        }
    }

    suspend fun fetchData(): ReportResponse<ItemReport> {
        return suspendCoroutine { continuation ->
            requestApi(appRepository.getItemReport(branchCode, null, null, 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ reportResponse ->
                        continuation.resume(reportResponse)
                    }, { err ->
                        continuation.resumeWithException(err)
                    }))
        }
    }

    private fun fillChart(data: ArrayList<ItemReport>) {
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