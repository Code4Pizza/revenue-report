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
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.home.report.adapter.DeleteReportAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_reject_report.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

class RejectReportFragment : BaseFragment() {

    private val rejectReportAdapter by androidLazy { DeleteReportAdapter() }
    private lateinit var infiniteScrollListener: InfiniteScrollListener

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
        requestReports()
    }

    private fun requestReports() {
        job = launch(UI) {
            try {
                val rejectReports = fetchData()
                rejectReportAdapter.addReports(rejectReports)
            } catch (e: Throwable) {
                rejectReportAdapter.dismissLoading()
                infiniteScrollListener.setLoading(false)
                context?.let { ErrorUtils.handleCommonError(it, e) }
            }
        }
    }

    private suspend fun fetchData(): ArrayList<RejectReport> {
        return suspendCoroutine { continuation ->
            requestApi(appRepository.getRejectReport("CNRoll_HDT")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ reportResponse ->
                        continuation.resume(reportResponse.data)
                    }, { err ->
                        continuation.resumeWithException(err)
                    }))
        }
    }
}