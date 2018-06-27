package com.fr.fbsreport.ui.report.bill

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseReportAdapter
import com.fr.fbsreport.base.BaseReportFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.base.INDEX_REPORT
import com.fr.fbsreport.model.BaseReport
import com.fr.fbsreport.model.BillReport
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.report.bill.adapter.BillReportDelegateAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BillReportFragment : BaseReportFragment<BillReport>() {

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
        return R.string.home_report_by_bill
    }

    override fun initReportList() {
        super.initReportList()
        reportAdapter = BaseReportAdapter(BillReportDelegateAdapter())
        reportAdapter.setOnReportClickListener(object : BaseReportAdapter.OnReportClickListener {
            override fun onReportClick(report: BaseReport) {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, BillReportDetailFragment.newInstance(report as BillReport))
            }
        })
        reportList.adapter = reportAdapter
        requestReports(true)
    }

    override fun requestReports(forceRefresh: Boolean) {
        requestApi(appRepository.getBillReport(branchCode, filter, limit, page)
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