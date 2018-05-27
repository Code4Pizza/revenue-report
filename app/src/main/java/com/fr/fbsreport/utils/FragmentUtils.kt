package com.fr.fbsreport.utils

import com.fr.fbsreport.base.BaseFragment
import java.util.*

class FragmentUtils {
    companion object {
        fun isAddFirstFragmentAgain(fragmentStack: Stack<BaseFragment>, nextFragment: BaseFragment): Boolean {
            synchronized(fragmentStack) {
                return fragmentStack[0]::class.simpleName.equals(nextFragment::class.simpleName)
            }
        }
    }
}