package com.fr.fbsreport.ui.home.analytic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.ReportPagerAdapter
import kotlinx.android.synthetic.main.fragment_analytic.*

class AnalyticFragment : BaseFragment() {

    private lateinit var pagerAdapter: ReportPagerAdapter

    companion object {
        @JvmStatic
        fun newInstance() = AnalyticFragment().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_analytic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = ReportPagerAdapter(childFragmentManager)
        view_pager!!.adapter = pagerAdapter
        view_pager.offscreenPageLimit = 4
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun hasToolbar(): Boolean {
        return false
    }
}
