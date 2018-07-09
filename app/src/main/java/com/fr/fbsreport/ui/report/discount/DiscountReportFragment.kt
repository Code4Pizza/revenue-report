package com.fr.fbsreport.ui.report.discount

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.extension.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.toast
import com.fr.fbsreport.model.DiscountReport
import com.fr.fbsreport.source.network.ErrorUtils
import com.fr.fbsreport.ui.report.BaseReportAdapter
import com.fr.fbsreport.ui.report.BaseReportTypeFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_discount_report.*
import java.util.concurrent.TimeUnit

class DiscountReportFragment : BaseReportTypeFragment() {

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = DiscountReportFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_discount_report
    }

    override fun getTitleIdToolbar(): Int? {
        return R.string.report_discount
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
        adapter = BaseReportAdapter(DiscountReportDelegateAdapter())
        reportList.adapter = adapter
        requestReports()
    }

    override fun requestReports() {
        requestApi(appRepository.getDiscountReport(branchCode, filter, limit, page)
                .subscribeOn(Schedulers.io())
                .materialize()
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.error?.let { ErrorUtils.handleCommonError(context, it) }
                    it
                }
                .filter { !it.isOnError }
                .dematerialize<List<DiscountReport>>()
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
        requestApi(appRepository.getDiscountReport(branchCode, filter, limit, page)
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