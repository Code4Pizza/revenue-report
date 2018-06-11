package com.fr.fbsreport.network

import com.fr.fbsreport.model.*
import io.reactivex.Single
import retrofit2.http.*

interface AppService {
    @FormUrlEncoded
    @POST("/user")
    fun register(@FieldMap fields: Map<String, String>): Single<User>

    @FormUrlEncoded
    @POST("/client/access_token")
    fun login(@FieldMap fields: Map<String, String>): Single<TokenModel>

    @GET("/user")
    fun getUserInfo(): Single<User>

    @PUT("/user")
    fun editUserInfo(): Single<User>

    @GET("/report?type=delete")
    fun getRejectReport(@Query("branch") branch: String): Single<BaseResponse.Report<RejectReport>>

    @GET("/report?type=bill")
    fun getBillReport(@Query("branch") branch: String): Single<BaseResponse.Report<BillReport>>

    @GET("/report?type=sale")
    fun getSaleReport(@Query("branch") branch: String): Single<BaseResponse.Report<SaleReport>>
}