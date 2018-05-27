package com.fr.fbsreport.base

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.fr.fbsreport.R
import com.fr.fbsreport.utils.EditTextUtils
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    private var fragmentStack: Stack<BaseFragment> = Stack()

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
}