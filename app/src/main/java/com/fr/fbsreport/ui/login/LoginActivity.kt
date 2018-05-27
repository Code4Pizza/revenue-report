package com.fr.fbsreport.ui.login

import android.content.Intent
import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.ui.brand.BrandActivity
import com.fr.fbsreport.ui.login.forgotpassword.ForgotPasswordActivity
import com.fr.fbsreport.widget.AppToolbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener({
            startActivity(Intent(this@LoginActivity, BrandActivity::class.java))
            finishAffinity()
        })
        txt_forgot_password.setOnClickListener({
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        })
        toolbar.setOnClickToolbarListener(object : AppToolbar.OnClickToolbarListener {
            override fun onItemLeft() {
                finish()
            }
        })
    }
}
