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

    override fun getDeleteReport(branch: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<DeleteReport>> {
        return appRemoteSource.getDeleteReport(branch, filter, limit, page)
    }

    override fun getBillReport(branch: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<BillReport>> {
        return appRemoteSource.getBillReport(branch, filter, limit, page)
    }

    override fun getSaleReport(branch: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<SaleReport>> {
        return appRemoteSource.getSaleReport(branch, filter, limit, page)
    }

    override fun getItemReport(branch: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<ItemReport>> {
        return appRemoteSource.getItemReport(branch, filter, limit, page)
    }
}