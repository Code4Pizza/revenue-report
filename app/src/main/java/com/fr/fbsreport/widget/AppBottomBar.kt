package com.fr.fbsreport.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.fr.fbsreport.R
import com.fr.fbsreport.extension.INDEX_ANALYTICS
import com.fr.fbsreport.extension.INDEX_REPORT
import com.fr.fbsreport.extension.INDEX_SETTING
import kotlinx.android.synthetic.main.view_app_bottom_bar.view.*

class AppBottomBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private var listener: OnClickBottomBarListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_app_bottom_bar, this, true)
        ll_analytics.setOnClickListener { setClicked(INDEX_ANALYTICS) }
        ll_report.setOnClickListener { setClicked(INDEX_REPORT) }
        ll_setting.setOnClickListener { setClicked(INDEX_SETTING) }
    }

    interface OnClickBottomBarListener {
        fun onItemBottomClick(position: Int)
    }

    fun setOnClickBottomBarListener(listener: OnClickBottomBarListener) {
        this.listener = listener
    }

    fun setClicked(position: Int) {
        when (position) {
            INDEX_ANALYTICS -> {
                img_analytics.setImageResource(R.drawable.icon_analytics_selected)
                txt_analytics.setTextColor(ContextCompat.getColor(context, R.color.orange))
                img_report.setImageResource(R.drawable.icon_report_normal)
                txt_report.setTextColor(ContextCompat.getColor(context, R.color.brightGray))
                img_setting.setImageResource(R.drawable.icon_setting_normal)
                txt_setting.setTextColor(ContextCompat.getColor(context, R.color.brightGray))
                onItemClicked(INDEX_ANALYTICS)
            }
            INDEX_REPORT -> {
                img_analytics.setImageResource(R.drawable.icon_analytics_normal)
                txt_analytics.setTextColor(ContextCompat.getColor(context, R.color.brightGray))
                img_report.setImageResource(R.drawable.icon_report_selected)
                txt_report.setTextColor(ContextCompat.getColor(context, R.color.orange))
                img_setting.setImageResource(R.drawable.icon_setting_normal)
                txt_setting.setTextColor(ContextCompat.getColor(context, R.color.brightGray))
                onItemClicked(INDEX_REPORT)
            }
            INDEX_SETTING -> {
                img_analytics.setImageResource(R.drawable.icon_analytics_normal)
                txt_analytics.setTextColor(ContextCompat.getColor(context, R.color.brightGray))
                img_report.setImageResource(R.drawable.icon_report_normal)
                txt_report.setTextColor(ContextCompat.getColor(context, R.color.brightGray))
                img_setting.setImageResource(R.drawable.icon_setting_selected)
                txt_setting.setTextColor(ContextCompat.getColor(context, R.color.orange))
                onItemClicked(INDEX_SETTING)
            }
        }
    }

    private fun onItemClicked(index: Int) {
        listener?.onItemBottomClick(index)
    }
}
