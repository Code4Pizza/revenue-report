package com.fr.fbsreport.source

import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.Dashboard
import com.fr.fbsreport.network.DataResponse
import com.fr.fbsreport.network.ReportResponse
import io.reactivex.Single

interface AppDataSource {

    fun register(username: String, email: String, password: String): Single<User>

    fun login(email: String, password: String): Single<TokenModel>

    fun getUserInfo(): Single<User>

    fun editUserInfo(): Single<User>

    fun getBranch(): Single<DataResponse<List<Branch>>>

    fun getDeleteReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<ReportResponse<DeleteReport>>

    fun getBillReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<ReportResponse<BillReport>>

    fun getSaleReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<ReportResponse<SaleReport>>

    fun getItemReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<ReportResponse<ItemReport>>

    fun getDashboard(branchCode: String, type: String, date: String?): Single<DataResponse<Dashboard>>
}
