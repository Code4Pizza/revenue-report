package com.fr.fbsreport.ui.home.report.reject

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.InfiniteScrollListener
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.model.RejectReport
import com.fr.fbsreport.network.BaseResponse
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.home.report.adapter.DeleteReportAdapter
import com.fr.fbsreport.utils.formatWithDot
import com.fr.fbsreport.widget.FilterDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_reject_report.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

class RejectReportFragment : BaseFragment() {

    private val rejectReportAdapter by androidLazy { DeleteReportAdapter() }
    private lateinit var infiniteScrollListener: InfiniteScrollListener
    private var filter: String? = null
    private var limit: Int? = 20
    private var page = 1

    companion object {
        @JvmStatic
        fun newInstance() = RejectReportFragment().apply {
        }
    }

    override fun getTitleToolbar(): String? {
        return "Báo cáo hủy đơn"
    }

    override fun getTextToolbarLeft(): String? {
        return "Back"
    }

    override fun onItemLeft() {
        super.onItemLeft()
        getBaseBottomTabActivity()?.onBackPressed()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reject_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_reject_report.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            infiniteScrollListener = InfiniteScrollListener({ requestReports() }, linearLayout)
            addOnScrollListener(infiniteScrollListener)
        }
        recycler_reject_report.adapter = rejectReportAdapter

        txt_filter.setOnClickListener({
            val filterDialog = FilterDialog.newInstance(txt_filter.text.toString())
            filterDialog.setOnClickFilterDialogListener(object : FilterDialog.OnClickFilterDialogListener {
                override fun onClickNone() {
                    setupFilter(null, 20, "Recent", false)
                }

                override fun onClickToday() {
                    setupFilter("today", null, "Today", true)
                }

                override fun onClickYesterday() {
                    setupFilter("yesterday", null, "Yesterday", true)
                }

                override fun onClickWeek() {
                    setupFilter("week", null, "Week", true)
                }

                override fun onClickMonth() {
                    setupFilter("month", null, "Month", true)
                }
            })
            getBaseActivity()?.showDialogFragment(filterDialog)
        })
        ll_total.visibility = View.GONE
        getBaseActivity()?.showLoading()
        requestReports()
    }

    fun setupFilter(filter: String?, limit: Int?, textFilter: String, totalVisible: Boolean) {
        this.filter = filter
        this.limit = limit
        this.page = 0
        txt_filter.text = textFilter
        txt_total.text = ""
        ll_total.visibility = if (totalVisible) View.VISIBLE else View.GONE
        filterReports()
    }

    private fun filterReports() {
        getBaseActivity()?.showLoading()
        rejectReportAdapter.clear()
        job = launch(UI) {
            try {
                val rejectReportsResponse = fetchData()
                getBaseActivity()?.hideLoading()
                rejectReportAdapter.addReports(rejectReportsResponse.data)
                txt_total.text = rejectReportsResponse.meta.totalPage.formatWithDot() + "đ"
            } catch (e: Throwable) {
                getBaseActivity()?.hideLoading()
                infiniteScrollListener.setLoading(false)
                context?.let { ErrorUtils.handleCommonError(it, e) }
            }
        }
    }

    private fun requestReports() {
        job = launch(UI) {
            try {
                val rejectReportsResponse = fetchData()
                getBaseActivity()?.hideLoading()
                rejectReportAdapter.addReports(rejectReportsResponse.data)
            } catch (e: Throwable) {
                getBaseActivity()?.hideLoading()
                infiniteScrollListener.setLoading(false)
                context?.let { ErrorUtils.handleCommonError(it, e) }
            }
        }
    }

    private suspend fun fetchData(): BaseResponse.Report<RejectReport> {
        return suspendCoroutine { continuation ->
            requestApi(appRepository.getRejectReport("CNRoll_HDT", filter, limit, page)
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