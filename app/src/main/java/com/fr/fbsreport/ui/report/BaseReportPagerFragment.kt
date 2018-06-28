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
        view_pager.setCurrentItem(1, true)
    }

    class ReportPagerAdapter(private val branchCode: String, fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            return when (position) {
                0 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_YESTERDAY)
                1 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_TODAY)
                2 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_WEEK)
                3 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_MONTH)
                4 -> BaseReportCustomFragment.newInstance(branchCode, FILTER_TYPE_CUSTOM)
                else -> throw IllegalArgumentException("Not valid pager")
            }
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Hôm qua"
                1 -> "Hôm nay"
                2 -> "Tuần"
                3 -> "Tháng"
                4 -> "Tùy chọn"
                else -> throw IllegalArgumentException("Not valid pager title")
            }
        }
    }

}