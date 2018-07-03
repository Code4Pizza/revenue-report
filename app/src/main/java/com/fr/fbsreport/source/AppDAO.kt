package com.fr.fbsreport.source

import android.arch.persistence.room.*
import com.fr.fbsreport.model.*
import io.reactivex.Maybe

/**
 * Created by framgia on 03/07/2018.
 */
@Dao
interface AppDAO {

    @Query("select * from tbl_branch")
    fun getBranches(): Maybe<List<Branch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBranches(branches: List<Branch>)

    @Query("delete from tbl_branch")
    fun deleteAllBranches()

    @Transaction
    fun updateBranches(branches: List<Branch>) {
        deleteAllBranches()
        insertAllBranches(branches)
    }

    @Query("select * from tbl_delete_report")
    fun getDeleteReports(): Maybe<List<DeleteReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDeleteReports(reports: List<DeleteReport>)

    @Query("delete from tbl_delete_report")
    fun deleteAllDeleteReports()

    @Transaction
    fun updateDeleteReports(reports: List<DeleteReport>) {
        deleteAllDeleteReports()
        insertDeleteReports(reports)
    }

    @Query("select * from tbl_bill_report")
    fun getBillReports(): Maybe<List<BillReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBillReports(reports: List<BillReport>)

    @Query("delete from tbl_bill_report")
    fun deleteAllBillReports()

    @Transaction
    fun updateBillReports(reports: List<BillReport>) {
        deleteAllBillReports()
        insertBillReports(reports)
    }

    @Query("select * from tbl_discount_report")
    fun getDiscountReports(): Maybe<List<DiscountReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiscountReports(reports: List<DiscountReport>)

    @Query("delete from tbl_discount_report")
    fun deleteAllDiscountReports()

    @Transaction
    fun updateDiscountReports(reports: List<DiscountReport>) {
        deleteAllDiscountReports()
        insertDiscountReports(reports)
    }

    @Query("select * from tbl_item_report")
    fun getItemReports(): Maybe<List<ItemReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemReports(reports: List<ItemReport>)

    @Query("delete from tbl_item_report")
    fun deleteAllItemReports()

    @Transaction
    fun updateItemReports(reports: List<ItemReport>) {
        deleteAllItemReports()
        insertItemReports(reports)
    }
}