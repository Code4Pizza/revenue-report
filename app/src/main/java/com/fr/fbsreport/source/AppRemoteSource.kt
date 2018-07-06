package com.fr.fbsreport.source

import com.fr.fbsreport.BuildConfig
import com.fr.fbsreport.extension.*
import com.fr.fbsreport.model.*
import com.fr.fbsreport.source.network.AppService
import com.fr.fbsreport.source.network.Dashboard
import io.reactivex.Flowable
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

    fun getBranch(): Flowable<List<Branch>> {
        return appService.getBranch()
                .map { it.data }
    }

    fun getDeleteReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<DeleteReport>> {
        return appService.getDeleteReport(branchCode, filter, limit, page)
                .map { it.data }
    }

    fun getBillReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<BillReport>> {
        return appService.getBillReport(branchCode, filter, limit, page)
                .map { it.data }
    }

    fun getDiscountReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<DiscountReport>> {
        return appService.getDiscountReport(branchCode, filter, limit, page)
                .map { it.data }
    }

    fun getItemReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<ItemReport>> {
        return appService.getItemReport(branchCode, filter, limit, page)
                .map { it.data }
    }

    fun getRevenueReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<RevenueReportCombine>> {
        return appService.getRevenueReport(branchCode, filter, limit, page)
                .map { it.data }
                .map {
                    val pairs = LinkedHashMap<String, ArrayList<RevenueReport>>()
                    it.forEach {
                        if (!pairs.containsKey(it.saleDate)) {
                            pairs[it.saleDate] = ArrayList()
                            pairs[it.saleDate]?.add(it)
                        } else {
                            pairs[it.saleDate]?.add(it)
                        }
                    }
                    val revenueReports = ArrayList<RevenueReportCombine>()

                    pairs.forEach {
                        val rrc = RevenueReportCombine()
                        rrc.saleDate = it.key
                        rrc.shift1 = it.value[0].shift
                        rrc.shift1Start = it.value[0].shiftStart
                        rrc.shift1End = it.value[0].shiftEnd
                        rrc.count1Pax = it.value[0].countPax
                        rrc.total1Sales = it.value[0].totalSales
                        rrc.drawer1Total = it.value[0].drawerTotal
                        rrc.overShortAmount1 = it.value[0].overShortAmount
                        rrc.shift2 = it.value[1].shift
                        rrc.shift2Start = it.value[1].shiftStart
                        rrc.shift2End = it.value[1].shiftEnd
                        rrc.count2Pax = it.value[1].countPax
                        rrc.total2Sales = it.value[1].totalSales
                        rrc.drawer2Total = it.value[1].drawerTotal
                        rrc.overShortAmount2 = it.value[1].overShortAmount
                        revenueReports.add(rrc)
                    }
                    revenueReports
                }
    }

    fun getDashboard(branchCode: String, type: String, date: String?, startDate: String?, endDate: String?): Flowable<Dashboard> {
        return appService.getDashboard(branchCode, type, date, startDate, endDate)
                .map { it.data }
    }
}