package com.fr.fbsreport.widget

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseDialog
import kotlinx.android.synthetic.main.view_filter_dialog.*

class FilterDialog : BaseDialog() {

    private var listener: OnClickFilterDialogListener? = null
    private var filter: String? = null

    companion object {
        @JvmStatic
        fun newInstance(filter: String) = FilterDialog().apply {
            this.filter = filter
        }
    }

    interface OnClickFilterDialogListener {
        fun onClickRecent()
        fun onClickToday()
        fun onClickYesterday()
        fun onClickWeek()
        fun onClickMonth()
    }

    fun setOnClickFilterDialogListener(listener: OnClickFilterDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            val colorBlack = ContextCompat.getColor(it, R.color.mediumBlack)
            when (filter) {
                "Recent" -> {
                    txt_recent.setTextColor(colorBlack)
                    txt_recent.isEnabled = false
                }
                "Today" -> {
                    txt_today.setTextColor(colorBlack)
                    txt_today.isEnabled = false
                }
                "Yesterday" -> {
                    txt_yesterday.setTextColor(colorBlack)
                    txt_yesterday.isEnabled = false
                }
                "Week" -> {
                    txt_week.setTextColor(colorBlack)
                    txt_week.isEnabled = false
                }
                "Month" -> {
                    txt_month.setTextColor(colorBlack)
                    txt_month.isEnabled = false
                }
            }
        }
        txt_recent.setOnClickListener {
            listener?.onClickRecent()
            dismiss()
        }
        txt_today.setOnClickListener {
            listener?.onClickToday()
            dismiss()
        }
        txt_yesterday.setOnClickListener {
            listener?.onClickYesterday()
            dismiss()
        }
        txt_week.setOnClickListener {
            listener?.onClickWeek()
            dismiss()
        }
        txt_month.setOnClickListener {
            listener?.onClickMonth()
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog.window?.attributes
        params?.width = resources.getDimensionPixelSize(R.dimen.filter_dialog_width);
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = params as android.view.WindowManager.LayoutParams
    }
}

