package com.fr.fbsreport.ui.report.item

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fr.fbsreport.R
import com.fr.fbsreport.extension.*
import com.fr.fbsreport.ui.report.BaseChartReportPagerFragment
import kotlinx.android.synthetic.main.fragment_base_chart_report_pager.*

class ItemChartReportPagerFragment : BaseChartReportPagerFragment() {

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = ItemChartReportPagerFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getTitleIdToolbar(): Int? {
        return R.string.item_report_pager_toolbar_title
    }

    override fun initViews() {
        pagerAdapter = ItemChartPagerAdapter(branchCode, childFragmentManager)
        view_pager.adapter = pagerAdapter
        view_pager.offscreenPageLimit = 4
        tab_layout.setupWithViewPager(view_pager)
        showLoading()
        view_pager.setCurrentItem(1, true)
    }

    class ItemChartPagerAdapter(private val branchCode: String, fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            return when (position) {
                0 -> ItemReportChartFragment.newInstance(branchCode, FILTER_TYPE_YESTERDAY)
                1 -> ItemReportChartFragment.newInstance(branchCode, FILTER_TYPE_TODAY)
                2 -> ItemReportChartFragment.newInstance(branchCode, FILTER_TYPE_WEEK)
                3 -> ItemReportChartFragment.newInstance(branchCode, FILTER_TYPE_MONTH)
                4 -> ItemReportCustomFragment.newInstance(branchCode, FILTER_TYPE_CUSTOM)
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