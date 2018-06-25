package com.fr.fbsreport.ui.home.analytic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.base.ReportPagerAdapter
import com.fr.fbsreport.extension.androidLazy
import kotlinx.android.synthetic.main.fragment_analytic.*

class AnalyticFragment : BaseFragment() {

    private val branchCode: String by androidLazy {
        arguments?.getString(EXTRA_BRANCH_CODE) ?: ""
    }
    private lateinit var pagerAdapter: ReportPagerAdapter

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = AnalyticFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_analytic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = ReportPagerAdapter(branchCode, childFragmentManager)
        view_pager!!.adapter = pagerAdapter
        view_pager.offscreenPageLimit = 4
        tab_layout.setupWithViewPager(view_pager)
    }
}