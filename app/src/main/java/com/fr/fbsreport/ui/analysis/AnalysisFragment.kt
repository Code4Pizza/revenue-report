package com.fr.fbsreport.ui.analysis

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.extension.EXTRA_BRANCH_CODE

class AnalysisFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = AnalysisFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_analysis
    }

    override fun initViews() {
    }
}