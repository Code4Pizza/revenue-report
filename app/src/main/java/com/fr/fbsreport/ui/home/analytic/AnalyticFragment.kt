package com.fr.fbsreport.ui.home.analytic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment

class AnalyticFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = AnalyticFragment().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_analytic, container, false)
    }
}