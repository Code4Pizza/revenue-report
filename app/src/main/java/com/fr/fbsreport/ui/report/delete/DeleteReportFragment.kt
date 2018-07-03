package com.fr.fbsreport.ui.report.delete

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseReportAdapter
import com.fr.fbsreport.base.BaseReportFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.model.BaseReport
import com.fr.fbsreport.model.DeleteReport
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.report.delete.adapter.DeleteReportDelegateAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DeleteReportFragment : BaseReportFragment<DeleteReport>() {

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = DeleteReportFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_delete_report
    }

    override fun getTitleIdToolbar(): Int? {
        return R.string.report_delete
    }

    override fun initReportList() {
        super.initReportList()
        reportAdapter = BaseReportAdapter(DeleteReportDelegateAdapter())
        reportAdapter.setOnReportClickListener(object : BaseReportAdapter.OnReportClickListener {
            override fun onReportClick(report: BaseReport) {
            }
        })
        reportList.adapter = reportAdapter
        requestReports()
    }

    override fun requestReports() {
        getBaseActivity()?.showLoading()
        requestApi(appRepository.getDeleteReport(branchCode, filter, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    getBaseActivity()?.hideLoading()
                    page++
                    reportAdapter.setReports(data)
                }, { err ->
                    getBaseActivity()?.hideLoading()
                    infiniteScrollListener.setLoading(false)
                    context?.let { ErrorUtils.handleCommonError(it, err) }
                }))
    }

    override fun requestMoreReports() {
        requestApi(appRepository.getDeleteReport(branchCode, filter, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    page++
                    reportAdapter.addReports(data)
                }, { err ->
                    infiniteScrollListener.setLoading(false)
                    context?.let { ErrorUtils.handleCommonError(it, err) }
                }))
    }
}