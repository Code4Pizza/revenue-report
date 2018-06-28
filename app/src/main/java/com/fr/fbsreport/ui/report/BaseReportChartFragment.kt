package com.fr.fbsreport.ui.report

import com.fr.fbsreport.base.*
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.network.Chart
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.collections.set

abstract class BaseReportChartFragment : BaseFragment() {

    val branchCode: String by androidLazy { arguments?.getString(EXTRA_BRANCH_CODE) ?: "" }
    val date: String by androidLazy { arguments?.getString(EXTRA_FILTER_DATE) ?: "" }

    val chartHashMap: LinkedHashMap<Int, Float> by androidLazy {
        val chartMap = LinkedHashMap<Int, Float>()
        when (date) {
            FILTER_TYPE_TODAY, FILTER_TYPE_YESTERDAY -> for (i in 8..24) {
                chartMap[i] = 0f
            }
            FILTER_TYPE_WEEK -> for (i in 2..8) {
                chartMap[i] = 0f
            }
            FILTER_TYPE_MONTH -> for (i in 1..Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)) {
                chartMap[i] = 0f
            }
        }
        chartMap
    }

    override fun initViews() {
        initChart()
    }

    abstract fun initChart()

    abstract fun requestData()

    abstract fun fillChart(charts: ArrayList<Chart>)
}