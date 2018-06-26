package com.fr.fbsreport.ui.report.sale

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseReportAdapter
import com.fr.fbsreport.base.BaseReportFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.model.SaleReport
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.report.sale.adapter.SaleReportDelegateAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SaleReportFragment : BaseReportFragment<SaleReport>() {

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = SaleReportFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_sale_report
    }

    override fun getTitleIdToolbar(): Int? {
        return R.string.home_report_promotion
    }

    override fun initReportList() {
        super.initReportList()
        reportAdapter = BaseReportAdapter(SaleReportDelegateAdapter())
        recyclerReport.adapter = reportAdapter
        getBaseActivity()?.showLoading()
        requestReports(true)
    }

    override fun requestReports(forceRefresh: Boolean) {
        requestApi(appRepository.getSaleReport(branchCode, filter, limit, page)
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