package com.example.ecomm.core.localDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecomm.features.cart.data.model.CartModel
import com.example.ecomm.features.cart.data.source.local.CartDao

@Database(
    entities = [CartModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        const val DATABASE_NAME = "ecomm_db"
    }
}