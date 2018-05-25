package com.fr.fbsreport.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.ui.main.MainActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            run {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }, 2000)
    }
}