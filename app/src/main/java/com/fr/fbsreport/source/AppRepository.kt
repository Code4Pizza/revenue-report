package com.fr.fbsreport.source

import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.BaseResponse
import io.reactivex.Single

class AppRepository(private val appRemoteSource: AppRemoteSource) : AppDataSource {

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

    override fun getBranch(): Single<BaseResponse.Default<List<Branch>>> {
        return appRemoteSource.getBranch()
    }

    override fun getDeleteReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<DeleteReport>> {
        return appRemoteSource.getDeleteReport(branchCode, filter, limit, page)
    }

    override fun getBillReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<BillReport>> {
        return appRemoteSource.getBillReport(branchCode, filter, limit, page)
    }

    override fun getSaleReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<SaleReport>> {
        return appRemoteSource.getSaleReport(branchCode, filter, limit, page)
    }

    override fun getItemReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<ItemReport>> {
        return appRemoteSource.getItemReport(branchCode, filter, limit, page)
    }
}