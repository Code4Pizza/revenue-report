package com.fr.fbsreport

import android.support.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.fr.fbsreport.di.AppComponent
import com.fr.fbsreport.di.AppModule
import com.fr.fbsreport.di.DaggerAppComponent

class App : MultiDexApplication() {

    companion object {
        @JvmStatic
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        Fresco.initialize(this)
    }
}