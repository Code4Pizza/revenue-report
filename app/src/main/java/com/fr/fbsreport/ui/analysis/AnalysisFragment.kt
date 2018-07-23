package com.fr.fbsreport.ui.analysis

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.extension.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.source.network.Dashboard
import com.fr.fbsreport.source.network.ErrorUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_analysis.*

class AnalysisFragment : BaseFragment() {

    private val branchCode: String by androidLazy { arguments?.getString(EXTRA_BRANCH_CODE) ?: "" }
    private lateinit var analysisAdapter: AnalysisAdapter

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
        analysisAdapter = AnalysisAdapter()
        dashboard_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = analysisAdapter
        }
        getDashboard()
    }

    private fun getDashboard() {
        requestApi(appRepository.getDashboard(branchCode, "a", "month", null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    getBaseActivity()?.hideLoading()
                    swipe_refresh.isRefreshing = false
                }
                .subscribe({ data ->
                    fillData(data)
                }, { err ->
                    context?.let {
                        ErrorUtils.handleCommonError(it, err)
                    }
                }))
    }

    private fun fillData(data: Dashboard) {
        analysisAdapter.setData(data)
    }
}