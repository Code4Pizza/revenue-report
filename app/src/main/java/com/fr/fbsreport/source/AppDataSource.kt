package com.fr.fbsreport.source

import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.BaseResponse
import io.reactivex.Single

interface AppDataSource {

    fun register(username: String, email: String, password: String): Single<User>

    fun login(email: String, password: String): Single<TokenModel>

    fun getUserInfo(): Single<User>

    fun editUserInfo(): Single<User>

    fun getRejectReport(branch: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<RejectReport>>

    fun getBillReport(branch: String): Single<BaseResponse.Report<BillReport>>

    fun getSaleReport(branch: String): Single<BaseResponse.Report<SaleReport>>
}
