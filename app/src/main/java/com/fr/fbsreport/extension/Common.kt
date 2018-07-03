package com.fr.fbsreport.extension

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fr.fbsreport.App
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

val AppCompatActivity.app: App
    get() = application as App

val Fragment.app: App
    get() = activity?.application as App

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun <T> androidLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun Context.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this).inflate(layoutId, null, attachToRoot)
}

fun Context.color(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

@SuppressLint("SimpleDateFormat")
fun String.getDayOfWeek(): Int {
    return try {
        val format1 = SimpleDateFormat("yyyy-MM-dd")
        val dt1 = format1.parse(this)
        val c = Calendar.getInstance()
        c.time = dt1
        c.get(Calendar.DAY_OF_WEEK)
    } catch (e: Exception) {
        throw Throwable("Time format invalid")
    }
}

@SuppressLint("SimpleDateFormat")
fun String.getDayOfMonth(): Int {
    return try {
        val format1 = SimpleDateFormat("yyyy-MM-dd")
        val dt1 = format1.parse(this)
        val c = Calendar.getInstance()
        c.time = dt1
        c.get(Calendar.DAY_OF_MONTH)
    } catch (e: Exception) {
        throw Throwable("Time format invalid")
    }
}

@SuppressLint("SimpleDateFormat")
fun String?.toDate(): Long {
    return try {
        val format1 = SimpleDateFormat("yyyy-MM-dd")
        format1.parse(this).time
    } catch (e: Exception) {
        throw Throwable("Time format invalid")
    }
}

@SuppressLint("SimpleDateFormat")
fun String?.formatDate(): String {
    return try {
        val myFormat = SimpleDateFormat("yyyy-MM-dd")
        val dt1 = myFormat.parse(this)
        val format1 = SimpleDateFormat("dd/MM")
        format1.format(dt1)
    } catch (e: Exception) {
        throw Throwable("Time format invalid")
    }
}

@SuppressLint("SimpleDateFormat")
fun String.displayDate(): String {
    return try {
        val date = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).parse(this)
        SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(date)
    } catch (e: Exception) {
        throw Throwable("Time format invalid")
    }
}

fun Any.formatWithDot(): String {
    return try {
        val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
        val symbols = formatter.decimalFormatSymbols
        symbols.groupingSeparator = '.'
        formatter.decimalFormatSymbols = symbols
        String.format("%sđ", formatter.format(this))
    } catch (e: Exception) {
        throw Throwable("Number invalid")
    }
}