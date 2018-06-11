package com.fr.fbsreport.ui.home.report.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.ReportPagerAdapter
import com.fr.fbsreport.ui.home.report.product.ProductReportFragment
import kotlinx.android.synthetic.main.fragment_bill_report.*

class BillReportFragment : BaseFragment() {

    var pagerAdapter: ReportPagerAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance() = BillReportFragment().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bill_report, container, false)
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