package com.fr.fbsreport.ui.login.forgotpassword

import android.os.Bundle
import android.view.View
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.widget.AppDialog
import com.fr.fbsreport.widget.AppToolbar
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        toolbar.setOnClickToolbarListener(object : AppToolbar.OnClickToolbarListener {
            override fun onItemLeft() {
                finish()
            }
        })
        btn_done.setOnClickListener({ showDialogFragment(AppDialog.newInstance()) })
    }
}
