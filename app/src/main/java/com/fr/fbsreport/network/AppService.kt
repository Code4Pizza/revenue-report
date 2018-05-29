package com.fr.fbsreport.network

import com.fr.fbsreport.model.TokenModel
import com.fr.fbsreport.model.User
import com.fr.fbsreport.network.body.LoginBody
import com.fr.fbsreport.network.body.RegisterBody
import io.reactivex.Single
import retrofit2.http.*

interface AppService {
    @FormUrlEncoded
    @POST("/user")
    fun register(@FieldMap fields: Map<String, String>): Single<User>

    @FormUrlEncoded
    @POST("/oauth/token")
    fun login(@FieldMap fields: Map<String, String>): Single<TokenModel>

    @GET("/user")
    fun getUserInfo(): Single<User>

    @PUT("/user")
    fun editUserInfo(): Single<User>

}