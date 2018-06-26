package com.fr.fbsreport.ui.analytic

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.base.ReportPagerAdapter
import com.fr.fbsreport.extension.androidLazy
import kotlinx.android.synthetic.main.fragment_analytic.*

class AnalyticFragment : BaseFragment() {

    private lateinit var pagerAdapter: ReportPagerAdapter

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = AnalyticFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun initViews() {
         initAnalyticPager()
    }

    private fun initAnalyticPager() {
//        pagerAdapter = ReportPagerAdapter(branchCode, childFragmentManager)
//        view_pager.adapter = pagerAdapter
//        view_pager.offscreenPageLimit = 4
//        tab_layout.setupWithViewPager(view_pager)
    }
}