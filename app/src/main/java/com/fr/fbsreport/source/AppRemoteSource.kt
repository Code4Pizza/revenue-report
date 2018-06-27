package com.fr.fbsreport.source

import com.fr.fbsreport.BuildConfig
import com.fr.fbsreport.base.*
import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.AppService
import com.fr.fbsreport.network.Dashboard
import com.fr.fbsreport.network.DataResponse
import com.fr.fbsreport.network.ReportResponse
import io.reactivex.Single

class AppRemoteSource(private val appService: AppService) : AppDataSource {

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

    override fun getBranch(): Single<DataResponse<List<Branch>>> {
        return appService.getBranch()
    }

    override fun getDeleteReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<ReportResponse<DeleteReport>> {
        return appService.getDeleteReport(branchCode, filter, limit, page)
    }

    override fun getBillReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<ReportResponse<BillReport>> {
        return appService.getBillReport(branchCode, filter, limit, page)
    }

    override fun getSaleReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<ReportResponse<SaleReport>> {
        return appService.getSaleReport(branchCode, filter, limit, page)
    }

    override fun getItemReport(branchCode: String, filter: String?, limit: Int?, page: Int): Single<ReportResponse<ItemReport>> {
        return appService.getItemReport(branchCode, filter, limit, page)
    }

    override fun getDashboard(branchCode: String, type: String, date: String?): Single<DataResponse<Dashboard>> {
        return appService.getDashboard(branchCode, type, date)
    }
}