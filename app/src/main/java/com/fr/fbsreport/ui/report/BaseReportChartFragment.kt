package com.fr.fbsreport.ui.report

import android.util.SparseArray
import com.fr.fbsreport.base.*
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.network.Chart
import java.util.ArrayList

abstract class BaseReportChartFragment : BaseFragment() {

    val branchCode: String by androidLazy { arguments?.getString(EXTRA_BRANCH_CODE) ?: "" }
    val date: String by androidLazy { arguments?.getString(EXTRA_FILTER_DATE) ?: "" }
    lateinit var xAxisValues: Array<String>
    val chartMap: SparseArray<Float> by androidLazy {
        val chartMap = SparseArray<Float>()
        when (date) {
            FILTER_TYPE_TODAY, FILTER_TYPE_YESTERDAY -> {
                for (i in 0 until 24) {
                    chartMap.put(i, 0f)
                }
            }
            FILTER_TYPE_WEEK -> {
                for (i in 0 until 7) {
                    chartMap.put(i, 0f)
                }
            }
        }
        chartMap
    }

    override fun initViews() {
        initChart()
        requestData()
    }

    abstract fun initChart()

    abstract fun requestData()

    abstract fun fillChart(charts: ArrayList<Chart>)
}