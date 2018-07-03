package com.fr.fbsreport.ui.analytic

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE

class AnalyticFragment : BaseFragment() {

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
    }
}