package com.fr.fbsreport.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt_sign_up.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
        })
    }
}
