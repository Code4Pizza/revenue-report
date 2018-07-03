package com.fr.fbsreport.ui.report.item

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseReportAdapter
import com.fr.fbsreport.base.BaseReportFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.base.INDEX_REPORT
import com.fr.fbsreport.model.BaseReport
import com.fr.fbsreport.model.ItemReport
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.report.item.adapter.ItemReportDelegateAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ItemReportFragment : BaseReportFragment<ItemReport>() {

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

    override fun initReportList() {
        super.initReportList()
        reportAdapter = BaseReportAdapter(ItemReportDelegateAdapter())
        reportAdapter.setOnReportClickListener(object : BaseReportAdapter.OnReportClickListener {
            override fun onReportClick(report: BaseReport) {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, DetailItemReportFragment.newInstance(report as ItemReport))
            }
        })
        reportList.adapter = reportAdapter
        getBaseActivity()?.showLoading()
        requestReports(true)
    }

    override fun requestReports(forceRefresh: Boolean) {
        requestApi(appRepository.getItemReport(branchCode, filter, limit, page)
                .doOnSubscribe {
                    if (forceRefresh) {
                        getBaseActivity()?.showLoading()
                        reportAdapter.clear()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    page++
                    getBaseActivity()?.hideLoading()
                    reportAdapter.addReports(response.data)
                }, { err ->
                    getBaseActivity()?.hideLoading()
                    infiniteScrollListener.setLoading(false)
                    context?.let { ErrorUtils.handleCommonError(it, err) }
                }))
    }
}