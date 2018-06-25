package com.fr.fbsreport.ui.home

import android.content.Context
import android.content.Intent
import android.view.View
import com.fr.fbsreport.R
import com.fr.fbsreport.base.*
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.ui.home.analytic.AnalyticFragment
import com.fr.fbsreport.ui.home.report.ReportFragment
import com.fr.fbsreport.ui.home.setting.SettingFragment
import com.fr.fbsreport.widget.AppBottomBar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.view_app_toolbar.view.*

class HomeActivity : BaseBottomTabActivity() {

    private val branchCode: String by androidLazy {
        intent.getStringExtra(EXTRA_BRANCH_CODE) ?: ""
    }

    companion object {
        fun newIntent(context: Context, branchCode: String): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(EXTRA_BRANCH_CODE, branchCode)
            return intent
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun initViews() {
        initBottomBar()
    }

    private fun initBottomBar() {
        bottom_bar.setOnClickBottombarListener(object : AppBottomBar.OnClickBottomBarListener {
            override fun onItemBottomClick(position: Int) {
                currentTab = position
                when (currentTab) {
                    INDEX_ANALYTICS -> {
                        addFragmentTab(INDEX_ANALYTICS, AnalyticFragment.newInstance(branchCode))
                    }
                    INDEX_REPORT -> {
                        addFragmentTab(INDEX_REPORT, ReportFragment.newInstance(branchCode))
                    }
                    INDEX_SETTING -> {
                        addFragmentTab(INDEX_SETTING, SettingFragment.newInstance())
                    }
                }
            }
        })
        bottom_bar.setClicked(INDEX_REPORT)
    }

    override fun updateToolbar(baseFragment: BaseFragment) {
        if (baseFragment.hasToolbar()) {
            toolbar.visibility = View.VISIBLE
        } else {
            toolbar.visibility = View.GONE
        }
        baseFragment.getTitleToolbar()?.let {
            toolbar.img_logo.visibility = View.GONE
            toolbar.txt_title.visibility = View.VISIBLE
            toolbar.txt_title.text = baseFragment.getTitleToolbar()
        } ?: run {
            toolbar.img_logo.visibility = View.VISIBLE
            toolbar.txt_title.visibility = View.GONE
        }
        baseFragment.getTextToolbarLeft()?.let {
            toolbar.txt_left.visibility = View.VISIBLE
            toolbar.txt_left.text = baseFragment.getTextToolbarLeft()
            val imageLeft = getDrawable(R.drawable.icon_back)
            imageLeft.setBounds(0, 0, imageLeft.intrinsicWidth, imageLeft.intrinsicHeight)
            toolbar.txt_left.setCompoundDrawables(imageLeft, null, null, null)
        } ?: run {
            toolbar.txt_left.visibility = View.GONE
        }
        toolbar.setOnClickToolbarListener(baseFragment)
    }
}
