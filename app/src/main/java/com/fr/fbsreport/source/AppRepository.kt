package com.fr.fbsreport.source

import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.BaseResponse
import io.reactivex.Single

class AppRepository(private val appRemoteSource: AppRemoteSource) : AppDataSource {

//    private object LazyHolder {
//        val INSTANCE = AppRepository(AppRemoteSource.instance)
//    }
//
//    companion object {
//        val instance: AppRepository by lazy {
//            LazyHolder.INSTANCE
//        }
//    }

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

    override fun getRejectReport(branch: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<RejectReport>> {
        return appRemoteSource.getRejectReport(branch, filter, limit, page)
    }

    override fun getBillReport(branch: String): Single<BaseResponse.Report<BillReport>> {
        return appRemoteSource.getBillReport(branch)
    }

    override fun getSaleReport(branch: String): Single<BaseResponse.Report<SaleReport>> {
        return appRemoteSource.getSaleReport(branch)
    }
}