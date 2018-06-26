package com.fr.fbsreport.ui.setting

import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment

class SettingFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment().apply {
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun initViews() {
    }
}