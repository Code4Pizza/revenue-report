package com.fr.fbsreport.ui.report.bill

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fr.fbsreport.extension.*
import com.fr.fbsreport.ui.report.BaseChartReportPagerFragment
import kotlinx.android.synthetic.main.fragment_base_chart_report_pager.*

/**
 * Created by framgia on 06/07/2018.
 */
class BillChartReportPagerFragment : BaseChartReportPagerFragment() {

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = BillChartReportPagerFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun initViews() {
        pagerAdapter = BillChartPagerAdapter(branchCode, childFragmentManager)
        view_pager.adapter = pagerAdapter
        view_pager.offscreenPageLimit = 4
        tab_layout.setupWithViewPager(view_pager)
        showLoading()
        view_pager.setCurrentItem(1, true)
    }

    class BillChartPagerAdapter(private val branchCode: String, fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            return when (position) {
                0 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_YESTERDAY)
                1 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_TODAY)
                2 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_WEEK)
                3 -> BillReportChartFragment.newInstance(branchCode, FILTER_TYPE_MONTH)
                4 -> BillReportCustomFragment.newInstance(branchCode, FILTER_TYPE_CUSTOM)
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