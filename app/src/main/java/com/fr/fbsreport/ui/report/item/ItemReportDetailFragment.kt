package com.fr.fbsreport.ui.report.item

import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.extension.EXTRA_ITEM_REPORT
import com.fr.fbsreport.extension.formatWithDot
import com.fr.fbsreport.model.ItemReport
import kotlinx.android.synthetic.main.fragment_item_report_detail.*

class ItemReportDetailFragment : BaseFragment() {

    private lateinit var itemReport: ItemReport

    companion object {
        @JvmStatic
        fun newInstance(itemReport: ItemReport) = ItemReportDetailFragment().apply {
            val bundle = Bundle()
            bundle.putSerializable(EXTRA_ITEM_REPORT, itemReport)
            arguments = bundle
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_item_report_detail
    }

    override fun getTitleIdToolbar(): Int? {
        return R.string.item_report_detail_toolbar_title
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
            itemReport = arguments!!.getSerializable(EXTRA_ITEM_REPORT) as ItemReport
        }
    }

    override fun initViews() {
        txt_code.text = itemReport.code
        txt_name.text = itemReport.name
        txt_category.text = itemReport.category
        txt_quantity.text = itemReport.quantity.toString()
        txt_price.text = itemReport.price.formatWithDot()
        txt_discount.text = itemReport.discount.formatWithDot()
        txt_tax.text = itemReport.tax.formatWithDot()
        txt_total.text = itemReport.total.formatWithDot()
    }
}