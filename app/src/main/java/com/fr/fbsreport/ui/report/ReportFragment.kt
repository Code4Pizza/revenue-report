package com.fr.fbsreport.ui.report

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.extension.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.INDEX_REPORT
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.ui.report.bill.BillReportFragment
import com.fr.fbsreport.ui.report.delete.DeleteReportFragment
import com.fr.fbsreport.ui.report.discount.DiscountReportFragment
import com.fr.fbsreport.ui.report.item.ItemReportFragment
import com.fr.fbsreport.ui.report.revenue.RevenueReportFragment
import kotlinx.android.synthetic.main.fragment_report.*

class ReportFragment : BaseFragment() {

    private val branchCode: String by androidLazy { arguments?.getString(EXTRA_BRANCH_CODE) ?: "" }

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = ReportFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }
    
    override fun getLayoutId(): Int {
        return R.layout.fragment_report
    }

    override fun initViews() {
        item_delete.setOnClickListener {
            getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, DeleteReportFragment.newInstance(branchCode))
        }
        item_bill.setOnClickListener {
            getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportFragment.newInstance(branchCode))
        }
        item_promotion.setOnClickListener {
            getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, DiscountReportFragment.newInstance(branchCode))
        }
        item_product.setOnClickListener {
            getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, ItemReportFragment.newInstance(branchCode))
        }
        item_revenue.setOnClickListener {
            getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, RevenueReportFragment.newInstance(branchCode))
        }
    }
}