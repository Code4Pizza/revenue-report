package com.fr.fbsreport

import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.fr.fbsreport.di.AppComponent
import com.fr.fbsreport.di.AppModule
import com.fr.fbsreport.di.DaggerAppComponent
import io.fabric.sdk.android.Fabric

class App : MultiDexApplication() {

    companion object {
        @JvmStatic
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        Fabric.with(this, Crashlytics())
    }
}