package com.fr.fbsreport.source.network

import com.fr.fbsreport.model.*
import io.reactivex.Flowable
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

    @GET("/branch")
    fun getBranch(): Flowable<DataResponse<List<Branch>>>

    @GET("/report?type=delete")
    fun getDeleteReport(@Query("branch") branchCode: String,
                        @Query("filter") filter: String?,
                        @Query("limit") limit: Int?,
                        @Query("page") page: Int): Flowable<ReportResponse<DeleteReport>>

    @GET("/report?type=bill")
    fun getBillReport(@Query("branch") branchCode: String,
                      @Query("filter") filter: String?,
                      @Query("limit") limit: Int?,
                      @Query("page") page: Int): Flowable<ReportResponse<BillReport>>

    @GET("/report?type=sale")
    fun getDiscountReport(@Query("branch") branchCode: String,
                          @Query("filter") filter: String?,
                          @Query("limit") limit: Int?,
                          @Query("page") page: Int): Flowable<ReportResponse<DiscountReport>>

    @GET("/report?type=item")
    fun getItemReport(@Query("branch") branchCode: String,
                      @Query("filter") filter: String?,
                      @Query("limit") limit: Int?,
                      @Query("page") page: Int): Flowable<ReportResponse<ItemReport>>

    @GET("/report?type=cash_count")
    fun getRevenueReport(@Query("branch") branchCode: String,
                         @Query("filter") filter: String?,
                         @Query("limit") limit: Int?,
                         @Query("page") page: Int): Flowable<ReportResponse<RevenueReport>>

    @GET("/report/dashboard")
    fun getDashboard(@Query("branch") branchCode: String,
                     @Query("date") date: String?,
                     @Query("start_date") startDate: String?,
                     @Query("end_date") endDate: String?): Flowable<DataResponse<Dashboard>>
}