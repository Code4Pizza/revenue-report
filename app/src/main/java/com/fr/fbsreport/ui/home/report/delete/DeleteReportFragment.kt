package com.fr.fbsreport.ui.home.report.delete

import android.os.Bundle
import android.view.View
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseReportFragment
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.model.DeleteReport
import com.fr.fbsreport.network.BaseResponse
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.home.report.delete.adapter.DeleteReportAdapter
import com.fr.fbsreport.ui.home.report.delete.adapter.DeleteReportDelegateAdapter
import com.fr.fbsreport.utils.formatWithDot
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_delete_report.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

class DeleteReportFragment : BaseReportFragment<BaseResponse.Report<DeleteReport>>() {

    private val reportAdapter by androidLazy { DeleteReportAdapter(DeleteReportDelegateAdapter()) }

    companion object {
        @JvmStatic
        fun newInstance() = DeleteReportFragment().apply {
        }
    }

    override fun getTitleToolbar(): String? {
        return "Delete Report"
    }

    override fun getTextToolbarLeft(): String? {
        return "Back"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerReport.adapter = reportAdapter
        getBaseActivity()?.showLoading()
        requestReports()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_item_report
    }

    override fun requestReports() {
        job = launch(UI) {
            try {
                val rejectReportsResponse = fetchData()
                getBaseActivity()?.hideLoading()
                reportAdapter.addReports(rejectReportsResponse.data)
            } catch (e: Throwable) {
                getBaseActivity()?.hideLoading()
                infiniteScrollListener.setLoading(false)
                context?.let { ErrorUtils.handleCommonError(it, e) }
            }
        }
    }

    override fun filterReports() {
        getBaseActivity()?.showLoading()
        reportAdapter.clear()
        job = launch(UI) {
            try {
                val rejectReportsResponse = fetchData()
                getBaseActivity()?.hideLoading()
                reportAdapter.addReports(rejectReportsResponse.data)
                txt_total.text = rejectReportsResponse.meta.totalPage.formatWithDot()
            } catch (e: Throwable) {
                getBaseActivity()?.hideLoading()
                infiniteScrollListener.setLoading(false)
                context?.let { ErrorUtils.handleCommonError(it, e) }
            }
        }
    }

    override suspend fun fetchData(): BaseResponse.Report<DeleteReport> {
        return suspendCoroutine { continuation ->
            requestApi(appRepository.getDeleteReport("CNRoll_HDT", filter, limit, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ reportResponse ->
                        page++
                        continuation.resume(reportResponse)
                    }, { err ->
                        continuation.resumeWithException(err)
                    }))
        }
    }
}