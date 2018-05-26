package com.fr.fbsreport.base

import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {

    public fun getBaseActivity(): BaseActivity? {
        if (activity is BaseActivity) {
            return activity as BaseActivity
        }
        return  null
    }
}