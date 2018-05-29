package com.fr.fbsreport.source

import com.fr.fbsreport.model.TokenModel
import com.fr.fbsreport.model.User
import com.fr.fbsreport.network.body.LoginBody
import io.reactivex.Single

class AppRepository private constructor(private val appRemoteSource: AppRemoteSource) : AppDataSource {

    private object LazyHolder {
        val INSTANCE = AppRepository(AppRemoteSource.instance)
    }

    companion object {
        val instance: AppRepository by lazy {
            LazyHolder.INSTANCE
        }
    }

    override fun register(username: String, email: String, password: String): Single<User> {
        return appRemoteSource.register(username, email, password)
    }

    override fun login(email: String, password: String): Single<TokenModel> {
        return appRemoteSource.login(email, password)
    }

    override fun getUserInfo(): Single<User> {
        return appRemoteSource.getUserInfo()
    }

    override fun editUserInfo(): Single<User> {
        return appRemoteSource.editUserInfo()
    }
}