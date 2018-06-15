package com.fr.fbsreport.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Long.formatWithDot(): String {
    val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
    val symbols = formatter.decimalFormatSymbols
    symbols.groupingSeparator = '.'
    formatter.decimalFormatSymbols = symbols
    return String.format("%sđ", formatter.format(this))
}