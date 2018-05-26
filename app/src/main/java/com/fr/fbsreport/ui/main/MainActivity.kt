package com.fr.fbsreport.ui.main

import android.content.Intent
import android.os.Bundle
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.ui.login.LoginActivity
import com.fr.fbsreport.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener({
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        })
        txt_sign_up.setOnClickListener({
            startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
        })
    }
}
