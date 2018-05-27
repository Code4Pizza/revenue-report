package com.fr.fbsreport.ui.home.report

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.ui.home.report.bill.BillReportFragment
import com.fr.fbsreport.ui.home.report.product.ProductReportFragment
import com.fr.fbsreport.ui.home.report.promotion.PromotionReportFragment
import com.fr.fbsreport.ui.home.report.reject.RejectReportFragment
import com.fr.fbsreport.widget.INDEX_REPORT
import com.fr.fbsreport.widget.ItemViewReport
import kotlinx.android.synthetic.main.fragment_report.*

class ReportFragment : BaseFragment(), View.OnClickListener {


    private var listener: OnFragmentInteractionListener? = null

    companion object {
        @JvmStatic
        fun newInstance() = ReportFragment().apply {
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.item_reject -> {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, RejectReportFragment.newInstance())
            }
            R.id.item_bill -> {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportFragment.newInstance())
            }
            R.id.item_promotion -> {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, PromotionReportFragment.newInstance())
            }
            R.id.item_product -> {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, ProductReportFragment.newInstance())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
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
