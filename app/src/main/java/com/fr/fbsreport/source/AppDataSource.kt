package com.fr.fbsreport.source

import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.Dashboard
import com.fr.fbsreport.network.DataResponse
import io.reactivex.Flowable
import io.reactivex.Single

interface AppDataSource {

    fun register(username: String, email: String, password: String): Single<User>

    fun login(email: String, password: String): Single<TokenModel>

    fun getUserInfo(): Single<User>

    fun editUserInfo(): Single<User>

    fun getBranch(): Flowable<DataResponse<List<Branch>>>

    fun getDeleteReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<DeleteReport>>

    fun getBillReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<BillReport>>

    fun getDiscountReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<DiscountReport>>

    fun getItemReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<ItemReport>>

    fun getDashboard(branchCode: String, type: String, date: String?, startDate: String?, endDate: String?): Single<DataResponse<Dashboard>>
}