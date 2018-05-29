package com.fr.fbsreport

import android.support.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.fr.fbsreport.source.UserPreference

class App : MultiDexApplication() {

    companion object {

        private var instance: App? = null

        fun getInstance(): App? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this)
    }
}