package com.fr.fbsreport.ui.chart.month

import android.os.Bundle
import android.view.View
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.BillReport
import com.fr.fbsreport.model.DeleteReport
import com.fr.fbsreport.model.ItemReport
import com.fr.fbsreport.model.SaleReport
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.network.ReportResponse
import com.fr.fbsreport.ui.chart.ChartAdapter
import com.fr.fbsreport.utils.formatWithDot
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_chart_month.*
import kotlinx.android.synthetic.main.item_view_chart_category_item.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

class ChartMonthFragment : BaseFragment() {

    private lateinit var chartAdapter: ChartAdapter
    private val branchCode: String by androidLazy {
        arguments?.getString(EXTRA_BRANCH_CODE) ?: ""
    }
    private var firstLoad = true
    private var total = 0L

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = ChartMonthFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_chart_month
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
        initChart()
    }

    private fun initChart() {
        line_chart.apply {
            val dataDate = ArrayList<String>()
            dataDate.add("")
            dataDate.add("5")
            dataDate.add("10")
            dataDate.add("15")
            dataDate.add("20")
            dataDate.add("25")
            dataDate.add("30")
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
        launch(UI) {
            try {
                val response = fetchDelete()
                fillChart(response.data)
                txt_total.text = total.formatWithDot()

                for (i in 0..4) {
                    context?.let {
                        val viewDelete = it.inflate(R.layout.item_view_chart_category_item)
                        viewDelete.txt_name.text = response.data[i].getFormatDate()
                        viewDelete.txt_total.text = response.data[i].total.formatWithDot()
                        if (i == 4) {
                            viewDelete.view_underline.visibility = View.GONE
                        }
                        ll_delete.addView(viewDelete)
                    }
                }

                val billResponse = fetchBill()
                for (i in 0..4) {
                    context?.let {
                        val viewDelete = it.inflate(R.layout.item_view_chart_category_item)
                        viewDelete.txt_name.text = billResponse.data[i].getFormatDate()
                        viewDelete.txt_total.text = billResponse.data[i].subTotal.formatWithDot()
                        if (i == 4) {
                            viewDelete.view_underline.visibility = View.GONE
                        }
                        ll_bill.addView(viewDelete)
                    }
                }

                val itemResponse = fetchItem()
                for (i in 0..4) {
                    context?.let {
                        val viewDelete = it.inflate(R.layout.item_view_chart_category_item)
                        viewDelete.txt_name.text = itemResponse.data[i].getFormatDate()
                        viewDelete.txt_total.text = itemResponse.data[i].total.formatWithDot()
                        if (i == 4) {
                            viewDelete.view_underline.visibility = View.GONE
                        }
                        ll_item.addView(viewDelete)
                    }
                }

                val saleResponse = fetchSale()
                for (i in 0..4) {
                    context?.let {
                        val viewDelete = it.inflate(R.layout.item_view_chart_category_item)
                        viewDelete.txt_name.text = saleResponse.data[i].getFormatDate()
                        viewDelete.txt_total.text = saleResponse.data[i].total.formatWithDot()
                        if (i == 4) {
                            viewDelete.view_underline.visibility = View.GONE
                        }
                        ll_sale.addView(viewDelete)
                    }
                }


                getBaseActivity()?.hideLoading()
            } catch (e: Throwable) {
                getBaseActivity()?.hideLoading()
                context?.let { ErrorUtils.handleCommonError(it, e) }
            }
        }
    }

    suspend fun fetchDelete(): ReportResponse<DeleteReport> {
        return suspendCoroutine { continuation ->
            requestApi(appRepository.getDeleteReport(branchCode, null, null, 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ reportResponse ->
                        continuation.resume(reportResponse)
                    }, { err ->
                        continuation.resumeWithException(err)
                    }))
        }
    }

    suspend fun fetchBill(): ReportResponse<BillReport> {
        return suspendCoroutine { continuation ->
            requestApi(appRepository.getBillReport(branchCode, null, null, 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ reportResponse ->
                        continuation.resume(reportResponse)
                    }, { err ->
                        continuation.resumeWithException(err)
                    }))
        }
    }

    suspend fun fetchSale(): ReportResponse<SaleReport> {
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

    suspend fun fetchItem(): ReportResponse<ItemReport> {
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

    private fun fillChart(data: ArrayList<DeleteReport>) {
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