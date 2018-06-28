package com.fr.fbsreport.base

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.fr.fbsreport.R
import com.fr.fbsreport.utils.FragmentUtils
import java.util.*
import kotlin.collections.HashMap

abstract class BaseBottomTabActivity : BaseActivity() {
    var fragmentMap: HashMap<Int, Stack<BaseFragment>> = HashMap()
    var currentTab: Int = -1
    var previousTab: Int = -1

    init {
        fragmentMap[INDEX_ANALYTICS] = Stack()
        fragmentMap[INDEX_REPORT] = Stack()
        fragmentMap[INDEX_SETTING] = Stack()
    }

    fun refreshFragmentTab() {
        Log.d(BaseBottomTabActivity::class.java.simpleName, "on refresh tab")
        popAllFragmentExceptFirst()
    }

    fun addFragmentTab(index: Int, fragment: BaseFragment) {
        synchronized(fragmentMap) {
            if (fragmentMap[index]!!.size == 0) {
                pushFragment(index, fragment, false, true)
                updateToolbar(fragment)
            } else {
                if (FragmentUtils.isAddFirstFragmentAgain(fragmentMap[index]!!, fragment)) {
                    if (currentTab == previousTab) {
                        popAllFragmentExceptFirst()
                    }
                    pushFragment(index, fragment, false, false)
                } else {
                    pushFragment(index, fragment, true, true)
                    updateToolbar(fragment)
                }
            }
            previousTab = currentTab
        }
    }

    private fun pushFragment(index: Int, fragment: BaseFragment, hasAnimation: Boolean, shouldAdd: Boolean) {
        synchronized(fragmentMap) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            if (hasAnimation) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            if (shouldAdd) {
                fragmentMap[index]?.push(fragment)
                fragmentTransaction.add(R.id.container_frame, fragment)
            } else {
                hideAllFragment(fragmentTransaction)
                fragmentTransaction.show(fragmentMap[index]!!.lastElement())
                updateToolbar(fragmentMap[index]!!.lastElement())
            }
            fragmentTransaction.commit()
        }
    }

    private fun hideAllFragment(fragmentTransaction: FragmentTransaction) {
        for (key in fragmentMap.keys) {
            for (i in 0 until (fragmentMap[key]?.size ?: 0)) {
                fragmentTransaction.hide(fragmentMap[key]?.get(i))
            }
        }
    }

    private fun popAllFragmentExceptFirst() {
        val stackFragment = fragmentMap[currentTab]
        val numberFragment = stackFragment!!.size.minus(1)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        for (i in 0 until numberFragment) {
            fragmentTransaction.remove(stackFragment.pop())
        }
        for (key in fragmentMap.keys) {
            for (i in 0 until (fragmentMap[key]?.size ?: 0)) {
                fragmentTransaction.hide(fragmentMap[key]?.get(i))
            }
        }
        val firstFragment = stackFragment.lastElement()
        fragmentTransaction.show(firstFragment)
        fragmentTransaction.commit()
    }

    private fun popFragmentTab() {
        hideKeyboard()
        synchronized(fragmentMap) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.remove(fragmentMap[currentTab]?.pop())
            fragmentTransaction.show(fragmentMap[currentTab]?.lastElement())
            fragmentTransaction.commit()
            fragmentMap[currentTab]?.lastElement()?.let { updateToolbar(it) }
        }
    }


    override fun onBackPressed() {
        if (fragmentMap[currentTab]!!.size > 1) {
            popFragmentTab()
        } else {
            super.onBackPressed()
        }
    }
}