package com.fr.fbsreport.ui.home.report.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.base.EXTRA_ITEM_REPORT
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.Item
import com.fr.fbsreport.model.ItemReport
import com.fr.fbsreport.utils.formatWithDot
import kotlinx.android.synthetic.main.fragment_detail_item.*
import kotlinx.android.synthetic.main.view_item_category.view.*

class DetailItemFragment : BaseFragment() {

    private lateinit var itemReport: ItemReport

    companion object {
        @JvmStatic
        fun newInstance(itemReport: ItemReport) = DetailItemFragment().apply {
            val bundle = Bundle()
            bundle.putSerializable(EXTRA_ITEM_REPORT, itemReport)
            arguments = bundle
        }
    }

    override fun getTitleToolbar(): String? {
        return "Chi tiết đơn hàng"
    }

    override fun getTextToolbarLeft(): String? {
        return "Quay lại"
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_sale_num.text = if (itemReport.saleNum.isEmpty()) "Unknown" else itemReport.saleNum
        txt_sale_date.text = itemReport.getFormatDate()
        txt_discount.text = itemReport.discount.formatWithDot()
        view.findViewById<TextView>(R.id.txt_total).text = itemReport.total.formatWithDot()
        ll_items.removeAllViewsInLayout()
        for (item in itemReport.items) {
            addViewDish(item)
        }
    }

    private fun addViewDish(item: Item) {
        context?.let {
            val itemCategory = it.inflate(R.layout.view_item_category)
            itemCategory.txt_category.text = item.category
            itemCategory.txt_item_code.text = item.itemCode
            itemCategory.txt_unit.text = item.unit
            itemCategory.txt_quantity.text = item.quantity.toString()
            itemCategory.txt_price.text = item.price.formatWithDot()
            itemCategory.txt_vat.text = item.tax.formatWithDot()
            itemCategory.findViewById<TextView>(R.id.txt_total).text = item.total.formatWithDot()
            ll_items.addView(itemCategory)
        }
    }
}