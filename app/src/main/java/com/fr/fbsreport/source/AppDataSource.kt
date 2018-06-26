package com.fr.fbsreport.source

import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.BaseResponse
import io.reactivex.Single

interface AppDataSource {

    fun register(username: String, email: String, password: String): Single<User>

    fun login(email: String, password: String): Single<TokenModel>

    fun getUserInfo(): Single<User>

    fun editUserInfo(): Single<User>

    fun getBranch(): Single<BaseResponse.Default<List<Branch>>>

    fun getDeleteReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<DeleteReport>>

    fun getBillReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<BillReport>>

    fun getSaleReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<SaleReport>>

    fun getItemReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<ItemReport>>

    fun getDashboard(branchCode: String, type: String, date: String?): Single<BaseResponse.Sections>
}
