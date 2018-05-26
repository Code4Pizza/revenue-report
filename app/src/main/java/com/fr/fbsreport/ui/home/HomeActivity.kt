package com.fr.fbsreport.ui.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.ui.report.ReportFragment
import com.fr.fbsreport.widget.AppBottomBar
import com.fr.fbsreport.widget.INDEX_REPORT
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottom_bar.setOnClickBottombarListener(object : AppBottomBar.OnClickBottomBarListener {
            override fun onItemBottomClick(position: Int) {
                Toast.makeText(this@HomeActivity, "Index " + position, Toast.LENGTH_SHORT).show()
                when (position) {
                    INDEX_REPORT -> {
                        addFragment(ReportFragment.newInstance(), R.id.container_frame)
                    }
                }
            }
        })
    }
}
