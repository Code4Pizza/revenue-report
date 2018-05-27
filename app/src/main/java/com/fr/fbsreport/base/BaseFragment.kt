package com.fr.fbsreport.base

import android.support.v4.app.Fragment
import android.util.Log

abstract class BaseFragment : Fragment() {

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            Log.d(BaseFragment::class.simpleName, "visible")
        } else {
            Log.d(BaseFragment::class.simpleName, "hide")
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            Log.d(BaseFragment::class.simpleName, "hidden")
        } else {
            Log.d(BaseFragment::class.simpleName, "show")
        }
    }

    public fun getBaseActivity(): BaseActivity? {
        if (activity is BaseActivity) {
            return activity as BaseActivity
        }
        return null
    }

    public fun getBaseBottomTabActivity(): BaseBottomTabActivity? {
        if (activity is BaseBottomTabActivity) {
            return activity as BaseBottomTabActivity
        }
        return null
    }
}