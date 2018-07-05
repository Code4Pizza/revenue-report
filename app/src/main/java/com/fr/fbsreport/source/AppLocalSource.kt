package com.fr.fbsreport.source

import com.fr.fbsreport.model.*
import com.fr.fbsreport.source.local.AppDatabase
import io.reactivex.Flowable

class AppLocalSource(private val appDatabase: AppDatabase) {

    fun getBranch(): Flowable<List<Branch>> {
        return appDatabase.appDao()
                .getBranches()
                .filter { it.isNotEmpty() }
                .toFlowable()
    }

    fun updateBranches(branch: List<Branch>) {
        appDatabase.appDao().updateBranches(branch)
    }

    fun getDeleteReport(): Flowable<List<DeleteReport>> {
        return appDatabase.appDao()
                .getDeleteReports()
                .filter { it.isNotEmpty() }
                .toFlowable()
    }

    fun updateDeleteReports(reports: List<DeleteReport>) {
        appDatabase.appDao().updateDeleteReports(reports)
    }

    fun getBillReport(): Flowable<List<BillReport>> {
        return appDatabase.appDao().getBillReports()
                .filter { it.isNotEmpty() }
                .toFlowable()
    }

    fun updateBillReports(reports: List<BillReport>) {
        appDatabase.appDao().updateBillReports(reports)
    }

    fun getDiscountReport(): Flowable<List<DiscountReport>> {
        return appDatabase.appDao().getDiscountReports()
                .filter { it.isNotEmpty() }
                .toFlowable()
    }

    fun updateDiscountReports(reports: List<DiscountReport>) {
        appDatabase.appDao().updateDiscountReports(reports)
    }

    fun getItemReport(): Flowable<List<ItemReport>> {
        return appDatabase.appDao().getItemReports()
                .filter { it.isNotEmpty() }
                .toFlowable()
    }

    fun updateItemReports(reports: List<ItemReport>) {
        appDatabase.appDao().updateItemReports(reports)
    }
}