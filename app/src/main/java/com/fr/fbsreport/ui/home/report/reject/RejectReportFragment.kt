package com.fr.fbsreport.ui.home.report.reject

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.BaseItem
import com.fr.fbsreport.model.RejectReport
import com.fr.fbsreport.network.BaseResponse
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.source.AppRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_reject_report.*

class RejectReportFragment : BaseFragment() {

    private lateinit var rejectReportAdapter: RejectReportAdapter

    companion object {
        @JvmStatic
        fun newInstance() = RejectReportFragment().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reject_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rejectReportAdapter = RejectReportAdapter(context)
        recycler_reject_report.layoutManager = LinearLayoutManager(context)
        recycler_reject_report.adapter = rejectReportAdapter
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


    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        requestApi(appRepository.getRejectReport("CNRoll_HDT")
                .doOnSubscribe { showLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ reportResponse ->
                    hideLoading()
                    showReportData(reportResponse)
                }, { err ->
                    hideLoading()
                    context?.let { ErrorUtils.handleCommonError(it, err) }
                }))
    }

    private fun showReportData(reportResponse: BaseResponse.Report<RejectReport>?) {
        if (reportResponse != null) {
            val data = ArrayList<BaseItem>()
            data.add(RejectReportAdapter.ItemRejectReportTitle())
            data.addAll(reportResponse.data)
            data.add(RejectReportAdapter.ItemRejectReportTotal(400000000))
            rejectReportAdapter.setItems(data)
        }
    }
}