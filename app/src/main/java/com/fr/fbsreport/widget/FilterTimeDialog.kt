package com.fr.fbsreport.widget

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.*
import kotlinx.android.synthetic.main.view_filter_dialog.*

class FilterTimeDialog : BaseDialog() {

    private lateinit var listener: OnClickFilterDialogListener
    private lateinit var time: String

    companion object {
        @JvmStatic
        fun newInstance(time: String, listener: OnClickFilterDialogListener) = FilterTimeDialog().apply {
            this.time = time
            this.listener = listener
        }
    }

    interface OnClickFilterDialogListener {
        fun onClickFilter(time: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            val colorBlack = ContextCompat.getColor(it, R.color.mediumBlack)
            when (time) {
                FILTER_TYPE_TODAY -> {
                    txt_today.setTextColor(colorBlack)
                    txt_today.isEnabled = false
                }
                FILTER_TYPE_YESTERDAY -> {
                    txt_yesterday.setTextColor(colorBlack)
                    txt_yesterday.isEnabled = false
                }
                FILTER_TYPE_WEEK -> {
                    txt_week.setTextColor(colorBlack)
                    txt_week.isEnabled = false
                }
                FILTER_TYPE_MONTH -> {
                    txt_month.setTextColor(colorBlack)
                    txt_month.isEnabled = false
                }
            }
        }
        txt_today.setOnClickListener {
            listener.onClickFilter(FILTER_TYPE_TODAY)
            dismiss()
        }
        txt_yesterday.setOnClickListener {
            listener.onClickFilter(FILTER_TYPE_YESTERDAY)
            dismiss()
        }
        txt_week.setOnClickListener {
            listener.onClickFilter(FILTER_TYPE_WEEK)
            dismiss()
        }
        txt_month.setOnClickListener {
            listener.onClickFilter(FILTER_TYPE_MONTH)
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog.window?.attributes
        params?.width = resources.getDimensionPixelSize(R.dimen.filter_dialog_width)
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = params as android.view.WindowManager.LayoutParams
    }
}