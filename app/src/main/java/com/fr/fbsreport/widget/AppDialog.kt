package com.fr.fbsreport.widget

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseDialog
import com.fr.fbsreport.extension.EXTRA_DIALOG_MESSAGE
import com.fr.fbsreport.extension.EXTRA_DIALOG_TITLE
import com.fr.fbsreport.extension.androidLazy
import kotlinx.android.synthetic.main.view_app_dialog.*
import java.lang.ClassCastException

open class AppDialog : BaseDialog() {

    companion object {
        @JvmStatic
        fun newInstance(title: String, message: String) = AppDialog().apply {
            val bundle = Bundle()
            bundle.putString(EXTRA_DIALOG_TITLE, title)
            bundle.putString(EXTRA_DIALOG_MESSAGE, message)
            arguments = bundle
        }
    }

    interface OnClickAppDialogListener {
        fun onClickConfirm()
    }

    private lateinit var listener: OnClickAppDialogListener
    private val title: String by androidLazy {
        arguments?.getString(EXTRA_DIALOG_TITLE) ?: ""
    }
    private val message: String by androidLazy {
        arguments?.getString(EXTRA_DIALOG_MESSAGE) ?: ""
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as OnClickAppDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnClickAppDialogListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_app_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_confirm.setOnClickListener {
            listener.onClickConfirm()
            dismiss()
        }
        txt_cancel.setOnClickListener {
            dismiss()
        }
        txt_title.text = title
        txt_message.text = message
    }
}
