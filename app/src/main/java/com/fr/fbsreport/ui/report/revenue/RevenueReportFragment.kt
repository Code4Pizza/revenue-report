package com.fr.fbsreport.ui.report.revenue

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.extension.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.INDEX_REPORT
import com.fr.fbsreport.extension.toast
import com.fr.fbsreport.model.RevenueReportCombine
import com.fr.fbsreport.source.network.ErrorUtils
import com.fr.fbsreport.ui.report.BaseReportAdapter
import com.fr.fbsreport.ui.report.BaseReportTypeFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_revenue.*

class RevenueReportFragment : BaseReportTypeFragment() {

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = RevenueReportFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_revenue
    }

    override fun getTitleIdToolbar(): Int? {
        return R.string.revenue_report_toolbar_title
    }

    override fun initViews() {
        super.initViews()
        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        swipe_refresh.setOnRefreshListener {
            page = 1
            requestReports()
        }
    }

    override fun initReportList() {
        super.initReportList()
        adapter = BaseReportAdapter(RevenueReportDelegateAdapter())
        adapter.setOnReportClickListener(object : BaseReportAdapter.OnReportClickListener {
            override fun onReportClick(report: ViewType) {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, RevenueReportDetailFragment.newInstance(report as RevenueReportCombine))

            }
        })
        reportList.adapter = adapter
        requestReports()
    }

    override fun requestReports() {
        requestApi(appRepository.getRevenueReport(branchCode, filter, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (!swipe_refresh.isRefreshing) showLoading()
                }
                .doOnTerminate {
                    hideLoading()
                    swipe_refresh.isRefreshing = false
                }
                .subscribe({
                    if (it.isEmpty()) {
                        context?.toast(getString(R.string.text_no_data))
                    } else {
                        page++
                        adapter.setReports(it)
                    }
                }, {
                    ErrorUtils.handleCommonError(context, it)
                }))
    }

    override fun requestMoreReports() {
    }
}