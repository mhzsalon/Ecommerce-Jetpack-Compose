package com.example.ecomm.core.localDB

import android.content.Context
import androidx.room.Room
import com.example.ecomm.features.cart.data.source.local.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object LocalDbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideCartDao(db: AppDatabase): CartDao = db.cartDao()
}