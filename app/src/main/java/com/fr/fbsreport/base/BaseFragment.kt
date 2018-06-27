package com.fr.fbsreport.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.App
import com.fr.fbsreport.source.AppRepository
import com.fr.fbsreport.source.UserPreference
import com.fr.fbsreport.widget.AppToolbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Named

abstract class BaseFragment : Fragment(), AppToolbar.OnClickToolbarListener {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var userPreference: UserPreference

    @field:[Inject Named("app_repository")]
    lateinit var appRepository: AppRepository

    abstract fun getLayoutId(): Int

    abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    open fun hasToolbar(): Boolean {
        return true
    }

    open fun getTitleToolbar(): String? {
        return null
    }

    @StringRes
    open fun getTitleIdToolbar(): Int? {
        return null
    }

    open fun getTextToolbarLeft(): String? {
        return null
    }

    open fun getTextIdToolbarLeft(): Int? {
        return null
    }

    override fun onItemLeft() {
        // Need override in fragment to register listener
    }

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