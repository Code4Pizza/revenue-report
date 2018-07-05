package com.fr.fbsreport.ui.report.bill

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.extension.EXTRA_BILL_REPORT
import com.fr.fbsreport.extension.displayDate
import com.fr.fbsreport.extension.formatWithDot
import com.fr.fbsreport.model.BillReport
import kotlinx.android.synthetic.main.fragment_bill_report_detail.*

class BillReportDetailFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(billReport: BillReport) = BillReportDetailFragment().apply {
            val bundle = Bundle()
            bundle.putSerializable(EXTRA_BILL_REPORT, billReport)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_bill_report_detail
    }

    override fun getTitleIdToolbar(): Int? {
        return R.string.bill_report_detail_toolbar_title
    }

    override fun getTextIdToolbarLeft(): Int? {
        return R.string.action_back
    }

    override fun onItemLeft() {
        super.onItemLeft()
        getBaseBottomTabActivity()?.onBackPressed()
    }

    override fun initViews() {
        arguments?.let {
            val billReport = it.getSerializable(EXTRA_BILL_REPORT) as BillReport
            txt_sale_num.text = if (billReport.saleNum.isEmpty()) "Unknown" else billReport.saleNum
            txt_sale_date.text = billReport.saleDate.displayDate()
            txt_table.text = billReport.tableId.toString()
            txt_discount.text = billReport.discount.formatWithDot()
            txt_tax.text = billReport.tax.formatWithDot()
            txt_total.text = billReport.subTotal.formatWithDot()
        }
    }
}