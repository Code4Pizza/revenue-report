@file:Suppress("MemberVisibilityCanBePrivate")

package com.fr.fbsreport.di

import com.fr.fbsreport.App
import com.fr.fbsreport.BuildConfig
import com.fr.fbsreport.network.AppService
import com.fr.fbsreport.network.AuthInterceptor
import com.fr.fbsreport.network.RxErrorHandling
import com.fr.fbsreport.source.AppRemoteSource
import com.fr.fbsreport.source.AppRepository
import com.fr.fbsreport.source.UserPreference
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule(val app: App) {
    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    fun provideUserPreference(app: App): UserPreference = UserPreference(app)

    @Provides
    @Singleton
    fun provideCache(app: App): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(app.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(userPreference: UserPreference): AuthInterceptor = AuthInterceptor(userPreference)

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, authInterceptor: AuthInterceptor, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideService(okHttpClient: OkHttpClient): AppService = Retrofit.Builder()
            .baseUrl("http://api.reporter.demo.ngocnh.info")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxErrorHandling.create())
            .client(okHttpClient)
            .build()
            .create(AppService::class.java)

    @Provides
    @Singleton
    fun provideAppRemoteSource(appService: AppService): AppRemoteSource = AppRemoteSource(appService)

    @Provides
    @Singleton
    @Named("app_repository")
    fun provideRepository(appRemoteSource: AppRemoteSource): AppRepository = AppRepository(appRemoteSource)

}