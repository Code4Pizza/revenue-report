package com.fr.fbsreport.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun <T> androidLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun Context.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this).inflate(layoutId, null, attachToRoot)
}
