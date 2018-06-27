package com.fr.fbsreport.ui.report

import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.base.FILTER_TYPE_TODAY
import com.fr.fbsreport.extension.androidLazy

abstract class BaseReportChartFragment : BaseFragment() {

    val branchCode: String by androidLazy { arguments?.getString(EXTRA_BRANCH_CODE) ?: "" }
    var date: String = FILTER_TYPE_TODAY
    lateinit var xAxisValues : Array<String>

    override fun initViews() {
        initChart()
        initFilter()
        requestData()
    }

    abstract fun initChart()

    abstract fun initFilter()

    abstract fun requestData()

    abstract fun fillChart()
}