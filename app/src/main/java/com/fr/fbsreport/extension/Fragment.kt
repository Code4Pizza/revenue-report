package com.fr.fbsreport.extension

import android.support.v4.app.Fragment
import com.fr.fbsreport.App
import java.util.*

val Fragment.app: App
    get() = activity?.application as App

fun Fragment.currentDate(): Int {
    return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
}

fun Fragment.currentMonth(): Int {
    return Calendar.getInstance().get(Calendar.MONTH)
}

fun Fragment.currentYear(): Int {
    return Calendar.getInstance().get(Calendar.YEAR)
}