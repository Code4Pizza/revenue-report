package com.fr.fbsreport.ui.report

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fr.fbsreport.R
import com.fr.fbsreport.base.*
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.ui.report.bill.BillReportChartFragment
import kotlinx.android.synthetic.main.fragment_base_report_pager.*

class BaseReportPagerFragment : BaseFragment() {

    val branchCode: String by androidLazy { arguments?.getString(EXTRA_BRANCH_CODE) ?: "" }
    private lateinit var pagerAdapter: ReportPagerAdapter

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = BaseReportPagerFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_report_pager
    }

    override fun initViews() {
        pagerAdapter = ReportPagerAdapter(branchCode, childFragmentManager)
        view_pager.adapter = pagerAdapter
        view_pager.offscreenPageLimit = 4
        tab_layout.setupWithViewPager(view_pager)
    }

    class ReportPagerAdapter(private val branchCode: String, fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            return when (position) {
                0 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_TODAY)
                1 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_YESTERDAY)
                2 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_YESTERDAY)
                3 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_YESTERDAY)
                else -> throw IllegalArgumentException("Not valid pager")
            }
        }

        override fun getCount(): Int {
            return 4
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Hôm nay"
                1 -> "Hôm qua"
                2 -> "Tuần"
                3 -> "Tháng"
                else -> throw IllegalArgumentException("Not valid pager title")
            }
        }
    }

}