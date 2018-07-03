package com.fr.fbsreport.di

import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.source.AppRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(activity: BaseActivity)

    fun inject(fragment: BaseFragment)

    fun inject(appRepository: AppRepository)
}