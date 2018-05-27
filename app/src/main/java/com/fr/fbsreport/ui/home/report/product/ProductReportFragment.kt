package com.fr.fbsreport.ui.home.report.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.ui.home.report.promotion.PromotionReportFragment

class ProductReportFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = ProductReportFragment().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_report, container, false)
    }
}