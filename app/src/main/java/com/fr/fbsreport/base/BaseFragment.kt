package com.fr.fbsreport.base

import android.support.v4.app.Fragment
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

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

    fun getBaseActivity(): BaseActivity? {
        if (activity is BaseActivity) {
            return activity as BaseActivity
        }
        return null
    }

    fun getBaseBottomTabActivity(): BaseBottomTabActivity? {
        if (activity is BaseBottomTabActivity) {
            return activity as BaseBottomTabActivity
        }
        return null
    }

    fun showLoading() {
        getBaseActivity()?.showLoading()
    }

    fun hideLoading() {
        getBaseActivity()?.hideLoading()
    }

    fun requestApi(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}