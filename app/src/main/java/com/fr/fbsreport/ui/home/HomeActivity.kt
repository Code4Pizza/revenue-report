package com.fr.fbsreport.ui.home

import android.os.Bundle
import android.widget.Toast
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseBottomTabActivity
import com.fr.fbsreport.ui.home.analytic.AnalyticFragment
import com.fr.fbsreport.ui.home.report.ReportFragment
import com.fr.fbsreport.ui.home.setting.SettingFragment
import com.fr.fbsreport.widget.AppBottomBar
import com.fr.fbsreport.widget.INDEX_ANALYTICS
import com.fr.fbsreport.widget.INDEX_REPORT
import com.fr.fbsreport.widget.INDEX_SETTING
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseBottomTabActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottom_bar.setOnClickBottombarListener(object : AppBottomBar.OnClickBottomBarListener {
            override fun onItemBottomClick(position: Int) {
                currentTab = position
                when (currentTab) {
                    INDEX_ANALYTICS -> {
                        addFragmentTab(INDEX_ANALYTICS, AnalyticFragment.newInstance())
                    }
                    INDEX_REPORT -> {
                        addFragmentTab(INDEX_REPORT, ReportFragment.newInstance())
                    }
                    INDEX_SETTING -> {
                        addFragmentTab(INDEX_SETTING, SettingFragment.newInstance())
                    }
                }
            }
        })
        bottom_bar.setClicked(INDEX_REPORT)
    }
}
