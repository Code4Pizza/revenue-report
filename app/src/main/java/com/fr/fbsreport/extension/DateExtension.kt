package com.fr.fbsreport.extension

import java.util.*

fun currentDate(): Int {
    return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
}

fun currentMonth(): Int {
    return Calendar.getInstance().get(Calendar.MONTH)
}

fun currentYear(): Int {
    return Calendar.getInstance().get(Calendar.YEAR)
}