package com.fr.fbsreport.ui.home.report.promotion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment

class PromotionReportFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = PromotionReportFragment().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_promotion_report, container, false)
    }
}