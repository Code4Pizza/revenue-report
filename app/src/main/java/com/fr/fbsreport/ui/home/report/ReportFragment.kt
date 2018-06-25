package com.fr.fbsreport.ui.home.report

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.base.INDEX_REPORT
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.ui.home.report.bill.BillReportFragment
import com.fr.fbsreport.ui.home.report.delete.DeleteReportFragment
import com.fr.fbsreport.ui.home.report.item.ItemReportFragment
import com.fr.fbsreport.ui.home.report.sale.SaleReportFragment
import kotlinx.android.synthetic.main.fragment_report.*

class ReportFragment : BaseFragment(), View.OnClickListener {

    private val branchCode: String by androidLazy {
        arguments?.getString(EXTRA_BRANCH_CODE) ?: ""
    }
    private var listener: OnFragmentInteractionListener? = null

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = ReportFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.item_reject -> {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, DeleteReportFragment.newInstance(branchCode))
            }
            R.id.item_bill -> {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportFragment.newInstance(branchCode))
            }
            R.id.item_promotion -> {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, SaleReportFragment.newInstance(branchCode))
            }
            R.id.item_product -> {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, ItemReportFragment.newInstance(branchCode))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item_reject.setOnClickListener(this)
        item_bill.setOnClickListener(this)
        item_promotion.setOnClickListener(this)
        item_product.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }
}
