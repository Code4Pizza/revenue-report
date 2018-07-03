package com.fr.fbsreport.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.fr.fbsreport.App
import com.fr.fbsreport.R
import com.fr.fbsreport.source.AppRepository
import com.fr.fbsreport.source.UserPreference
import com.fr.fbsreport.utils.EditTextUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject
import javax.inject.Named

abstract class BaseActivity : AppCompatActivity() {

    private var fragmentStack: Stack<BaseFragment> = Stack()
    private var loadingDialog: Dialog? = null

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var userPreference: UserPreference

    @field:[Inject Named("app_repository")]
    lateinit var appRepository: AppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        setContentView(getLayoutId())
        initViews()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initViews()

    open fun updateToolbar(baseFragment: BaseFragment) {
    }

//    fun addFragment(fragment: BaseFragment, hasAnimation: Boolean = true) {
//        if (fragmentStack.contains(fragment)) return
//
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        if (fragmentStack.size >= 1) {
//            fragmentTransaction.hide(fragmentStack.lastElement())
//        }
//        if (hasAnimation) {
//            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
//        }
//        fragmentStack.push(fragment)
//        fragmentTransaction.add(R.id.container_frame, fragment)
//        fragmentTransaction.commit()
//    }

//    fun popFragment() {
//        hideKeyboard()
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.remove(fragmentStack.pop())
//        fragmentTransaction.show(fragmentStack.lastElement())
//        fragmentTransaction.commit()
//    }

    private fun initDialog() {
        loadingDialog = Dialog(this)
        loadingDialog!!.setContentView(R.layout.view_dialog_loading)
        loadingDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog!!.setCancelable(false)
        loadingDialog!!.setCanceledOnTouchOutside(false)
    }

    fun showLoading() {
        if (isFinishing) return
        runOnUiThread {
            hideKeyboard()
            if (loadingDialog == null) {
                initDialog()
            }
            if (!loadingDialog!!.isShowing) {
                loadingDialog!!.show()
            }
        }
    }

    fun hideLoading() {
        if (isFinishing) return
        runOnUiThread {
            hideKeyboard()
            if (loadingDialog != null) {
                if (loadingDialog!!.isShowing) {
                    loadingDialog!!.dismiss()
                }
            }
        }
    }

    fun hideKeyboard() {
        EditTextUtils.hideKeyboard(this)
        EditTextUtils.hideSoftKeyboard(this)
    }

//    override fun onBackPressed() {
//        if (fragmentStack.size > 1) {
//            popFragment()
//        } else {
//            super.onBackPressed()
//        }
//    }

    fun showDialogFragment(dialog: BaseDialog) {
        runOnUiThread {
            val fragmentManager = supportFragmentManager
            dialog.show(fragmentManager, dialog::class.java.name)
        }
    }

    fun requestApi(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}