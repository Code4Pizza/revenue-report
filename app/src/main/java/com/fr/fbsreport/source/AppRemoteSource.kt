package com.fr.fbsreport.source

import com.fr.fbsreport.BuildConfig
import com.fr.fbsreport.base.*
import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.AppService
import com.fr.fbsreport.network.BaseResponse
import io.reactivex.Single

class AppRemoteSource(val appService: AppService) : AppDataSource {

    override fun register(username: String, email: String, password: String): Single<User> {
        val fields = HashMap<String, String>()
        fields[FIELD_USERNAME] = username
        fields[FIELD_EMAIL] = email
        fields[FIELD_PASSWORD] = password

        return appService.register(fields)
    }

    override fun login(email: String, password: String): Single<TokenModel> {
        val fields = HashMap<String, String>()
        fields[FIELD_GRANT_TYPE] = "password"
        fields[FIELD_USERNAME] = email
        fields[FIELD_PASSWORD] = password
        fields[FIELD_CLIENT_ID] = BuildConfig.CLIENT_ID
        fields[FIELD_CLIENT_SECRET] = BuildConfig.CLIENT_SECRET

        return appService.login(fields)
    }

    override fun getUserInfo(): Single<User> {
        return appService.getUserInfo()
    }

    override fun editUserInfo(): Single<User> {
        return appService.editUserInfo()
    }

    override fun getDeleteReport(branch: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<DeleteReport>> {
        return appService.getDeleteReport(branch, filter, limit, page)
    }

    override fun getBillReport(branch: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<BillReport>> {
        return appService.getBillReport(branch, filter, limit, page)
    }

    override fun getSaleReport(branch: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<SaleReport>> {
        return appService.getSaleReport(branch, filter, limit, page)
    }

    override fun getItemReport(branch: String, filter: String?, limit: Int?, page: Int): Single<BaseResponse.Report<ItemReport>> {
        return appService.getItemReport(branch, filter, limit, page)
    }
}