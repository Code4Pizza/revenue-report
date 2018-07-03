package com.fr.fbsreport.source

import com.fr.fbsreport.App
import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.Dashboard
import com.fr.fbsreport.network.DataResponse
import com.fr.fbsreport.utils.CommonUtils
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppRepository(private val appRemoteSource: AppRemoteSource, private val appLocalSource: AppLocalSource) : AppDataSource {

    @Inject
    lateinit var app: App

    init {
        App.component.inject(this)
    }

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

    override fun getBranch(): Flowable<DataResponse<List<Branch>>> {
        val localSource = appLocalSource.getBranch()
                .subscribeOn(Schedulers.io())

        val remoteSource = appRemoteSource.getBranch()
                .doOnSuccess({
                    appLocalSource.updateBranches(it.data)
                })
                .subscribeOn(Schedulers.io())

        return Maybe.concat(localSource, remoteSource)
    }

    override fun getDeleteReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<DeleteReport>> {
        val localSource = appLocalSource.getDeleteReport()
                .subscribeOn(Schedulers.io())

        val remoteSource = appRemoteSource.getDeleteReport(branchCode, filter, limit, page)
                .doOnSuccess({
                    // Only save local first page
                    if (page == 1) appLocalSource.updateDeleteReports(it)
                })
                .subscribeOn(Schedulers.io())

        return if (CommonUtils.checkConnection(app)) remoteSource.toFlowable()
        else localSource.toFlowable()
    }

    override fun getBillReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<BillReport>> {
        val localSource = appLocalSource.getBillReport()
                .subscribeOn(Schedulers.io())

        val remoteSource = appRemoteSource.getBillReport(branchCode, filter, limit, page)
                .doOnSuccess({
                    if (page == 1) appLocalSource.updateBillReports(it)
                })
                .subscribeOn(Schedulers.io())

        return if (CommonUtils.checkConnection(app)) remoteSource.toFlowable()
        else localSource.toFlowable()
    }

    override fun getDiscountReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<DiscountReport>> {
        val localSource = appLocalSource.getDiscountReport()
                .subscribeOn(Schedulers.io())

        val remoteSource = appRemoteSource.getDiscountReport(branchCode, filter, limit, page)
                .doOnSuccess({
                    if (page == 1) appLocalSource.updateDiscountReports(it)
                })
                .subscribeOn(Schedulers.io())

        return if (CommonUtils.checkConnection(app)) remoteSource.toFlowable()
        else localSource.toFlowable()
    }

    override fun getItemReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<ItemReport>> {
        val localSource = appLocalSource.getItemReport()
                .subscribeOn(Schedulers.io())

        val remoteSource = appRemoteSource.getItemReport(branchCode, filter, limit, page)
                .doOnSuccess({
                    if (page == 1) appLocalSource.updateItemReports(it)
                })
                .subscribeOn(Schedulers.io())

        return if (CommonUtils.checkConnection(app)) remoteSource.toFlowable()
        else localSource.toFlowable()
    }

    override fun getDashboard(branchCode: String, type: String, date: String?, startDate: String?, endDate: String?): Single<DataResponse<Dashboard>> {
        return appRemoteSource.getDashboard(branchCode, type, date, startDate, endDate)
    }
}