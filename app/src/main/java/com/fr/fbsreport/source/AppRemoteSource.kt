package com.fr.fbsreport.source

import com.fr.fbsreport.BuildConfig
import com.fr.fbsreport.base.*
import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.AppService
import com.fr.fbsreport.network.Dashboard
import com.fr.fbsreport.network.DataResponse
import io.reactivex.Maybe
import io.reactivex.Single

class AppRemoteSource(private val appService: AppService) {

    fun register(username: String, email: String, password: String): Single<User> {
        val fields = HashMap<String, String>()
        fields[FIELD_USERNAME] = username
        fields[FIELD_EMAIL] = email
        fields[FIELD_PASSWORD] = password

        return appService.register(fields)
    }

    fun login(email: String, password: String): Single<TokenModel> {
        val fields = HashMap<String, String>()
        fields[FIELD_GRANT_TYPE] = "password"
        fields[FIELD_USERNAME] = email
        fields[FIELD_PASSWORD] = password
        fields[FIELD_CLIENT_ID] = BuildConfig.CLIENT_ID
        fields[FIELD_CLIENT_SECRET] = BuildConfig.CLIENT_SECRET

        return appService.login(fields)
    }

    fun getUserInfo(): Single<User> {
        return appService.getUserInfo()
    }

    fun editUserInfo(): Single<User> {
        return appService.editUserInfo()
    }

    fun getBranch(): Maybe<DataResponse<List<Branch>>> {
        return appService.getBranch()
    }

    fun getDeleteReport(branchCode: String, filter: String?, limit: Int?, page: Int): Maybe<List<DeleteReport>> {
        return appService.getDeleteReport(branchCode, filter, limit, page)
                .map { it.data }
    }

    fun getBillReport(branchCode: String, filter: String?, limit: Int?, page: Int): Maybe<List<BillReport>> {
        return appService.getBillReport(branchCode, filter, limit, page)
                .map { it.data }
    }

    fun getDiscountReport(branchCode: String, filter: String?, limit: Int?, page: Int): Maybe<List<DiscountReport>> {
        return appService.getDiscountReport(branchCode, filter, limit, page)
                .map { it.data }
    }

    fun getItemReport(branchCode: String, filter: String?, limit: Int?, page: Int): Maybe<List<ItemReport>> {
        return appService.getItemReport(branchCode, filter, limit, page)
                .map { it.data }
    }

    fun getDashboard(branchCode: String, type: String, date: String?, startDate: String?, endDate: String?): Single<DataResponse<Dashboard>> {
        return appService.getDashboard(branchCode, type, date, startDate, endDate)
    }
}