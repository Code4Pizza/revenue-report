package com.fr.fbsreport.source

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fr.fbsreport.model.*

/**
 * Created by framgia on 03/07/2018.
 */
@Database(entities = [Branch::class, DeleteReport::class, BillReport::class, DiscountReport::class, ItemReport::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDAO
}