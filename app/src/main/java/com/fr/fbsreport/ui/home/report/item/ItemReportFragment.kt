package com.fr.fbsreport.ui.home.report.item

import android.os.Bundle
import android.view.View
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseReportFragment
import com.fr.fbsreport.base.EXTRA_BRANCH_CODE
import com.fr.fbsreport.base.INDEX_REPORT
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.model.ItemReport
import com.fr.fbsreport.network.BaseResponse
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.home.report.item.adapter.ItemReportAdapter
import com.fr.fbsreport.ui.home.report.item.adapter.ItemReportDelegateAdapter
import com.fr.fbsreport.utils.formatWithDot
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_delete_report.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

class ItemReportFragment : BaseReportFragment<BaseResponse.Report<ItemReport>>() {

    private val branchCode: String by androidLazy {
        arguments?.getString(EXTRA_BRANCH_CODE) ?: ""
    }
    private val reportAdapter by androidLazy { ItemReportAdapter(ItemReportDelegateAdapter()) }

    companion object {
        @JvmStatic
        fun newInstance(branchCode: String) = ItemReportFragment().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_BRANCH_CODE, branchCode)
            arguments = bundle
        }
    }

    override fun getTitleToolbar(): String? {
        return "Báo cáo theo mặt hàng"
    }

    override fun getTextToolbarLeft(): String? {
        return "Quay lại"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reportAdapter.setOnItemReportAdapterClickListener(object : ItemReportAdapter.OnItemReportAdapterClickListener {
            override fun onClickItemReport(itemReport: ItemReport) {
                getBaseBottomTabActivity()?.addFragmentTab(INDEX_REPORT, DetailItemFragment.newInstance(itemReport))
            }
        })
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

    override suspend fun fetchData(): BaseResponse.Report<ItemReport> {
        return suspendCoroutine { continuation ->
            requestApi(appRepository.getItemReport(branchCode, filter, limit, page)
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