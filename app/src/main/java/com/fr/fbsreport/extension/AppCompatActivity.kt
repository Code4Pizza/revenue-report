package com.fr.fbsreport.extension

import android.support.v7.app.AppCompatActivity
import com.fr.fbsreport.App

val AppCompatActivity.app: App
    get() = application as App

