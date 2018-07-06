package com.fr.fbsreport.ui.report.revenue

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.extension.EXTRA_ITEM_REPORT
import com.fr.fbsreport.extension.formatWithDot
import com.fr.fbsreport.model.RevenueReportCombine
import kotlinx.android.synthetic.main.fragment_revenue_report_detail.*

class RevenueReportDetailFragment : BaseFragment() {

    private lateinit var itemReport: RevenueReportCombine

    companion object {
        @JvmStatic
        fun newInstance(itemReport: RevenueReportCombine) = RevenueReportDetailFragment().apply {
            val bundle = Bundle()
            bundle.putSerializable(EXTRA_ITEM_REPORT, itemReport)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_revenue_report_detail
    }

    override fun getTitleIdToolbar(): Int? {
        return R.string.revenue_report_detail_toolbar_title
    }

    override fun getTextIdToolbarLeft(): Int? {
        return R.string.action_back
    }

    override fun onItemLeft() {
        super.onItemLeft()
        getBaseBottomTabActivity()?.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemReport = arguments!!.getSerializable(EXTRA_ITEM_REPORT) as RevenueReportCombine
        }
    }

    override fun initViews() {
        txt_sale_date.text = itemReport.saleDate
        txt_shift_1_start.text = itemReport.shift1Start
        txt_shift_1_end.text = itemReport.shift1End
        txt_shift_1_pax.text = itemReport.count1Pax.toString()
        txt_shift_1_total.text = itemReport.total1Sales.formatWithDot()
        txt_shift_2_start.text = itemReport.shift2Start
        txt_shift_2_end.text = itemReport.shift2End
        txt_shift_2_pax.text = itemReport.count2Pax.toString()
        txt_shift_2_total.text = itemReport.total2Sales.formatWithDot()
        txt_total.text = (itemReport.total1Sales + itemReport.total2Sales).formatWithDot()
    }
}