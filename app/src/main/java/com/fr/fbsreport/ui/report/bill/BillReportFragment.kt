package com.fr.fbsreport.ui.report.bill

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.extension.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.INDEX_REPORT
import com.fr.fbsreport.extension.toast
import com.fr.fbsreport.model.BillReport
import com.fr.fbsreport.source.network.ErrorUtils
import com.fr.fbsreport.ui.report.BaseReportAdapter
import com.fr.fbsreport.ui.report.BaseReportTypeFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_bill_report.*
import java.util.concurrent.TimeUnit

class BillReportFragment : BaseReportTypeFragment() {

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = BillReportFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_bill_report
    }

    override fun getTitleIdToolbar(): Int? {
        return R.string.report_by_bill
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
        adapter = BaseReportAdapter(BillReportDelegateAdapter())
        adapter.setOnReportClickListener(object : BaseReportAdapter.OnReportClickListener {
            override fun onReportClick(report: ViewType) {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportDetailFragment.newInstance(report as BillReport))
            }
        })
        reportList.adapter = adapter
        requestReports()
    }

    override fun requestReports() {
        requestApi(appRepository.getBillReport(branchCode, filter, limit, page)
                .subscribeOn(Schedulers.io())
                .materialize()
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.error?.let { ErrorUtils.handleCommonError(context, it) }
                    it
                }
                .filter { !it.isOnError }
                .dematerialize<List<BillReport>>()
                .debounce(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (!swipe_refresh.isRefreshing) showLoading()
                }
                .doOnTerminate {
                    hideLoading()
                    swipe_refresh.isRefreshing = false
                }
                .doOnNext {
                    if (it.isEmpty()) {
                        context?.toast(getString(R.string.text_no_data))
                    } else {
                        page++
                        adapter.setReports(it)
                    }
                }
                .subscribe())
    }

    override fun requestMoreReports() {
        requestApi(appRepository.getBillReport(branchCode, filter, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    reportList.post { adapter.addLoading() }
                }
                .doFinally {
                    isLoading = false
                }
                .subscribe({
                    page++
                    adapter.addReports(it)
                }, {
                    adapter.hideLoading()
                    ErrorUtils.handleCommonError(context, it)
                }))
    }
}