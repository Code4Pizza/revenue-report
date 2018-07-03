package com.fr.fbsreport.ui.report.discount

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseReportAdapter
import com.fr.fbsreport.base.BaseReportFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.model.DiscountReport
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.report.discount.adapter.DiscountReportDelegateAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DiscountReportFragment : BaseReportFragment<DiscountReport>() {

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

    override fun initReportList() {
        super.initReportList()
        reportAdapter = BaseReportAdapter(DiscountReportDelegateAdapter())
        reportList.adapter = reportAdapter
        requestReports()
    }

    override fun requestReports() {
        getBaseActivity()?.showLoading()
        requestApi(appRepository.getDiscountReport(branchCode, filter, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    page++
                    getBaseActivity()?.hideLoading()
                    reportAdapter.setReports(data)
                }, { err ->
                    getBaseActivity()?.hideLoading()
                    infiniteScrollListener.setLoading(false)
                    context?.let { ErrorUtils.handleCommonError(it, err) }
                }))
    }

    override fun requestMoreReports() {
        requestApi(appRepository.getDiscountReport(branchCode, filter, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    page++
                    getBaseActivity()?.hideLoading()
                    reportAdapter.addReports(data)
                }, { err ->
                    getBaseActivity()?.hideLoading()
                    infiniteScrollListener.setLoading(false)
                    context?.let { ErrorUtils.handleCommonError(it, err) }
                }))
    }
}