package com.fr.fbsreport.ui.report.item

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.ui.report.BaseReportTypeFragment
import com.fr.fbsreport.extension.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.INDEX_REPORT
import com.fr.fbsreport.model.BaseReport
import com.fr.fbsreport.model.ItemReport
import com.fr.fbsreport.source.network.ErrorUtils
import com.fr.fbsreport.ui.report.BaseReportAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_item_report.*
import java.util.concurrent.TimeUnit

class ItemReportFragment : BaseReportTypeFragment() {

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = ItemReportFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_item_report
    }

    override fun getTitleIdToolbar(): Int? {
        return R.string.report_by_item
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
        adapter = BaseReportAdapter(ItemReportDelegateAdapter())
        adapter.setOnReportClickListener(object : BaseReportAdapter.OnReportClickListener {
            override fun onReportClick(report: BaseReport) {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, ItemReportDetailFragment.newInstance(report as ItemReport))
            }
        })
        reportList.adapter = adapter
        requestReports()
    }

    override fun requestReports() {
        requestApi(appRepository.getItemReport(branchCode, filter, limit, page)
                .subscribeOn(Schedulers.io())
                .materialize()
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.error?.let { ErrorUtils.handleCommonError(context, it) }
                    it
                }
                .filter { !it.isOnError }
                .dematerialize<List<ItemReport>>()
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
                    page++
                    adapter.setReports(it)
                }
                .subscribe())
    }

    override fun requestMoreReports() {
        requestApi(appRepository.getItemReport(branchCode, filter, limit, page)
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