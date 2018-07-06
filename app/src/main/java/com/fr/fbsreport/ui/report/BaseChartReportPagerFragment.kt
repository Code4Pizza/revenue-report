package com.fr.fbsreport.ui.report

import android.support.v4.app.FragmentPagerAdapter
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.extension.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.androidLazy

abstract class BaseChartReportPagerFragment : BaseFragment() {

    val branchCode: String by androidLazy { arguments?.getString(EXTRA_BRANCH_CODE) ?: "" }
    protected lateinit var pagerAdapter: FragmentPagerAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_chart_report_pager
    }

    override fun getTextIdToolbarLeft(): Int? {
        return R.string.action_back
    }

    override fun onItemLeft() {
        super.onItemLeft()
        getBaseBottomTabActivity()?.onBackPressed()
    }
}