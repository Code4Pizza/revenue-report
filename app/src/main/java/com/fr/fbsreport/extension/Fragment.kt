package com.fr.fbsreport.extension

import android.support.v4.app.Fragment
import com.fr.fbsreport.App

val Fragment.app: App
    get() = activity?.application as App