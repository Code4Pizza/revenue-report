package com.fr.fbsreport.source

import com.fr.fbsreport.BuildConfig
import com.fr.fbsreport.base.*
import com.fr.fbsreport.model.TokenModel
import com.fr.fbsreport.model.User
import com.fr.fbsreport.network.AppService
import com.fr.fbsreport.network.RestClient
import io.reactivex.Single

class AppRemoteSource(private val appService: AppService) : AppDataSource {

    private object LazyHolder {
        val INSTANCE = AppRemoteSource(RestClient.createService())
    }

    companion object {
        val instance: AppRemoteSource by lazy {
            LazyHolder.INSTANCE
        }
    }

    override fun register(username: String, email: String, password: String): Single<User> {
        val fields = HashMap<String, String>()
        fields[FIELD_USERNAME] = username
        fields[FIELD_EMAIL] = email
        fields[FIELD_PASSWORD] = password

        return appService.register(fields)
    }

    override fun login(email: String, password: String): Single<TokenModel> {
        val fields = HashMap<String, String>()
        fields[FIELD_GRANT_TYPE] = "password"
        fields[FIELD_USERNAME] = email
        fields[FIELD_PASSWORD] = password
        fields[FIELD_CLIENT_ID] = BuildConfig.CLIENT_ID
        fields[FIELD_CLIENT_SECRET] = BuildConfig.CLIENT_SECRET

        return appService.login(fields)
    }

    override fun getUserInfo(): Single<User> {
        return appService.getUserInfo()
    }

    override fun editUserInfo(): Single<User> {
        return appService.editUserInfo()
    }
}