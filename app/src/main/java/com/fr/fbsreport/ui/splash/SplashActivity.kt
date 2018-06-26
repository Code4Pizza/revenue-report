package com.fr.fbsreport.ui.splash

import android.content.Intent
import android.os.Handler
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.ui.branch.BranchActivity
import com.fr.fbsreport.ui.main.MainActivity

class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initViews() {
        Handler().postDelayed({
            run {
                if (userPreference.isSignedIn()) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
                finish()
            }
        }, 2000)
    }
}