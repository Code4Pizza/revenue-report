package com.fr.fbsreport.source

import com.fr.fbsreport.App
import com.fr.fbsreport.model.*
import com.fr.fbsreport.source.network.Dashboard
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppRepository(private val appRemoteSource: AppRemoteSource, private val appLocalSource: AppLocalSource) {

    @Inject
    lateinit var app: App

    init {
        App.component.inject(this)
    }

    fun register(username: String, email: String, password: String): Single<User> {
        return appRemoteSource.register(username, email, password)
    }

    fun login(email: String, password: String): Single<TokenModel> {
        return appRemoteSource.login(email, password)
    }

    fun getUserInfo(): Single<User> {
        return appRemoteSource.getUserInfo()
    }

    fun editUserInfo(): Single<User> {
        return appRemoteSource.editUserInfo()
    }

    fun getBranch(): Flowable<List<Branch>> {
        val localSource = appLocalSource.getBranch()
                .doOnNext {
                    println("Local next " + Thread.currentThread().name)
                }
                .doOnComplete {
                    println("Local completed " + Thread.currentThread().name)
                }

        val remoteSource = appRemoteSource.getBranch()
                .doOnNext {
                    println("Remote next " + Thread.currentThread().name)
                    Observable
                            .fromCallable {
                                appLocalSource.updateBranches(it)
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe()
                }
                .doOnError { println("Remote error " + Thread.currentThread().name) }
                .doOnComplete { println("Remote completed " + Thread.currentThread().name) }

        return Flowable.concatArrayEager(localSource, remoteSource)
    }

    fun getDeleteReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<DeleteReport>> {
        val localSource = appLocalSource.getDeleteReport()
                .doOnNext {
                    println("Local next " + Thread.currentThread().name)
                }
                .doOnComplete {
                    println("Local completed " + Thread.currentThread().name)
                }

        val remoteSource = appRemoteSource.getDeleteReport(branchCode, filter, limit, page)
                .doOnNext {
                    println("Remote next " + Thread.currentThread().name)
                    // Only save local first page
                    if (page == 1) {
                        Observable
                                .fromCallable {
                                    appLocalSource.updateDeleteReports(it)
                                }
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe()
                    }
                }
                .doOnError { println("Remote error " + Thread.currentThread().name) }
                .doOnComplete { println("Remote completed " + Thread.currentThread().name) }

        if (page == 1) return Flowable.concatArrayEager(localSource, remoteSource)
        return remoteSource
    }

    fun getBillReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<BillReport>> {
        val localSource = appLocalSource.getBillReport()

        val remoteSource = appRemoteSource.getBillReport(branchCode, filter, limit, page)
                .doOnNext {
                    if (page == 1) {
                        Observable
                                .fromCallable {
                                    appLocalSource.updateBillReports(it)
                                }
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe()
                    }
                }

        if (page == 1) return Flowable.concatArrayEager(localSource, remoteSource)
        return remoteSource
    }

    fun getDiscountReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<DiscountReport>> {
        val localSource = appLocalSource.getDiscountReport()

        val remoteSource = appRemoteSource.getDiscountReport(branchCode, filter, limit, page)
                .doOnNext {
                    if (page == 1) {
                        Observable
                                .fromCallable {
                                    appLocalSource.updateDiscountReports(it)
                                }
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe()
                    }
                }

        if (page == 1) return Flowable.concatArrayEager(localSource, remoteSource)
        return remoteSource
    }

    fun getItemReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<ItemReport>> {
        val localSource = appLocalSource.getItemReport()

        val remoteSource = appRemoteSource.getItemReport(branchCode, filter, limit, page)
                .doOnNext {
                    if (page == 1) {
                        Observable
                                .fromCallable {
                                    appLocalSource.updateItemReports(it)
                                }
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe()
                    }
                }

        if (page == 1) return Flowable.concatArrayEager(localSource, remoteSource)
        return remoteSource
    }

    fun getRevenueReport(branchCode: String, filter: String?, limit: Int?, page: Int): Flowable<List<RevenueReportCombine>> {
//        val localSource = appLocalSource.getRevenueReport()

        val remoteSource = appRemoteSource.getRevenueReport(branchCode, filter, limit, page)
//                .doOnNext {
//                    if (page == 1) {
//                        Observable
//                                .fromCallable {
//                                    appLocalSource.updateRevenueReports(it)
//                                }
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(Schedulers.io())
//                                .subscribe()
//                    }
//                }

       // if (page == 1) return Flowable.concatArrayEager(localSource, remoteSource)
        return remoteSource
    }
    
    fun getDashboard(branchCode: String, type: String, date: String?, startDate: String?, endDate: String?): Flowable<Dashboard> {
        return appRemoteSource.getDashboard(branchCode, type, date, startDate, endDate)
    }
}