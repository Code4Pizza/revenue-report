package com.fr.fbsreport.ui.report

import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.extension.*
import com.fr.fbsreport.source.network.Dashboard
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.collections.set

abstract class BaseReportChartFragment : BaseFragment() {

    val branchCode: String by androidLazy { arguments?.getString(EXTRA_BRANCH_CODE) ?: "" }
    val filter: String by androidLazy { arguments?.getString(EXTRA_FILTER_DATE) ?: "" }
    var startDay: Int? = null
    var startMonth: Int? = null
    var startYear: Int? = null
    var endDay: Int? = null
    var endMonth: Int? = null
    var endYear: Int? = null

    val chartHashMap: LinkedHashMap<Int, Float> by androidLazy {
        val chartMap = LinkedHashMap<Int, Float>()
        when (filter) {
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
        initChart()
    }

    abstract fun initChart()

    abstract fun requestData()

    abstract fun fillChart(data: Dashboard)
}