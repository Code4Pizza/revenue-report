package com.fr.fbsreport.source

import com.fr.fbsreport.model.*
import com.fr.fbsreport.network.DataResponse
import io.reactivex.Maybe

/**
 * Created by framgia on 03/07/2018.
 */
class AppLocalSource(private val appDatabase: AppDatabase) {

    fun getBranch(): Maybe<DataResponse<List<Branch>>> {
        return appDatabase.appDao().getBranches()
                .map { DataResponse(it) }
    }

    fun updateBranches(branch: List<Branch>) {
        appDatabase.appDao().updateBranches(branch)
    }

    fun getDeleteReport(): Maybe<List<DeleteReport>> {
        return appDatabase.appDao().getDeleteReports()
    }

    fun updateDeleteReports(reports: List<DeleteReport>) {
        appDatabase.appDao().updateDeleteReports(reports)
    }

    fun getBillReport(): Maybe<List<BillReport>> {
        return appDatabase.appDao().getBillReports()
    }

    fun updateBillReports(reports: List<BillReport>) {
        appDatabase.appDao().updateBillReports(reports)
    }

    fun getDiscountReport(): Maybe<List<DiscountReport>> {
        return appDatabase.appDao().getDiscountReports()
    }

    fun updateDiscountReports(reports: List<DiscountReport>) {
        appDatabase.appDao().updateDiscountReports(reports)
    }

    fun getItemReport(): Maybe<List<ItemReport>> {
        return appDatabase.appDao().getItemReports()
    }

    fun updateItemReports(reports: List<ItemReport>) {
        appDatabase.appDao().updateItemReports(reports)
    }
}