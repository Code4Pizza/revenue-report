package com.fr.fbsreport.ui.home.report.reject

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.BaseItem
import com.fr.fbsreport.model.RejectReport
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

        var datas = ArrayList<BaseItem>()
        datas.add(RejectReportAdapter.ItemRejectReportTitle())
        datas.add(RejectReport("20/05/2018", "1234ABCD12", 10000000, "Đổi hàng"))
        datas.add(RejectReport("20/05/2018", "1234ABCD12", 10000000, "Đổi hàng"))
        datas.add(RejectReport("20/05/2018", "1234ABCD12", 10000000, "Đổi hàng"))
        datas.add(RejectReport("20/05/2018", "1234ABCD12", 10000000, "Đổi hàng"))
        datas.add(RejectReport("20/05/2018", "1234ABCD12", 10000000, "Đổi hàng"))
        datas.add(RejectReport("20/05/2018", "1234ABCD12", 10000000, "Đổi hàng"))
        datas.add(RejectReportAdapter.ItemRejectReportTotal(400000000))
        rejectReportAdapter.setItems(datas)

        recycler_reject_report.layoutManager = LinearLayoutManager(context)
        recycler_reject_report.adapter = rejectReportAdapter
    }
}