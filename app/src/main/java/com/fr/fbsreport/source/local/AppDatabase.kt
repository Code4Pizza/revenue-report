package com.fr.fbsreport.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fr.fbsreport.model.*

@Database(entities = [
    Branch::class,
    DeleteReport::class,
    BillReport::class,
    DiscountReport::class,
    ItemReport::class,
    RevenueReport::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDAO
}