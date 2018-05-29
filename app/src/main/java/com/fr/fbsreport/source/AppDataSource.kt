package com.fr.fbsreport.source

import com.fr.fbsreport.model.TokenModel
import com.fr.fbsreport.model.User
import io.reactivex.Single

interface AppDataSource {

    fun register(username: String, email: String, password: String): Single<User>

    fun login(email: String, password: String): Single<TokenModel>

    fun getUserInfo(): Single<User>

    fun editUserInfo(): Single<User>
}
