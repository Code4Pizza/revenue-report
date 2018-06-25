package com.fr.fbsreport.ui.login.forgotpassword

import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.widget.AppDialog
import com.fr.fbsreport.widget.AppToolbar
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_forgot_password
    }

    override fun initViews() {
        toolbar.setOnClickToolbarListener(object : AppToolbar.OnClickToolbarListener {
            override fun onItemLeft() {
                finish()
            }
        })
        btn_done.setOnClickListener { showDialogFragment(AppDialog.newInstance()) }
    }
}
