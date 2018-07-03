package com.fr.fbsreport.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseDialog
import kotlinx.android.synthetic.main.view_app_dialog.*

class AppDialog : BaseDialog() {

    companion object {
        @JvmStatic
        fun newInstance() = AppDialog().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_app_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_confirm.setOnClickListener {
            Toast.makeText(context, "Click Confirm", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

}
