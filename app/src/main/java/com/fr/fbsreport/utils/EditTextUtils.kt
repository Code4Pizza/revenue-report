package com.fr.fbsreport.utils

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import java.util.regex.Pattern

class EditTextUtils {
    companion object {
        fun hideKeyboard(activity: Activity?) {
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }

        fun hideSoftKeyboard(activity: Activity?) {
            val view = activity?.currentFocus
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        fun isEmailValid(email: String): Boolean {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }
    }
}