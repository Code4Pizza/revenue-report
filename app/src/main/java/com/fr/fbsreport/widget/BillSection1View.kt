package com.fr.fbsreport.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.fr.fbsreport.R
import com.fr.fbsreport.network.ReportChart

class BillSection1View @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_bill_section_1, this, true)
    }

    fun initViews(data: ArrayList<ReportChart>, listener: OnClickListener) {

    }
}