package com.example.ecomm.features.cart.di

import com.example.ecomm.features.cart.data.repositories.CartRepositoryImpl
import com.example.ecomm.features.cart.data.source.local.CartDao
import com.example.ecomm.features.cart.data.source.local.CartLocalDataSource
import com.example.ecomm.features.cart.data.source.local.CartLocalDataSourceImpl
import com.example.ecomm.features.cart.domain.repositories.CartRepository
import com.example.ecomm.features.cart.domain.usecases.AddToCartUsecase
import com.example.ecomm.features.cart.domain.usecases.ClearCartUseCase
import com.example.ecomm.features.cart.domain.usecases.DeleteCartUseCase
import com.example.ecomm.features.cart.domain.usecases.FetchCartUsecase
import com.example.ecomm.features.cart.domain.usecases.UpdateCartQuantityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object CartModule {

    @Provides
    @Singleton
    fun provideCartLocalDataSource(
        cartDao: CartDao
    ): CartLocalDataSource = CartLocalDataSourceImpl(cartDao)


    @Provides
    @Singleton
    fun provideCartRepository(
        localDataSource: CartLocalDataSource
    ): CartRepository = CartRepositoryImpl(localDataSource)


    @Provides
    @Singleton
    fun provideAddToCartUsecase(
        repository: CartRepository
    ): AddToCartUsecase = AddToCartUsecase(repository)


    @Provides
    @Singleton
    fun provideFetchCartUsecase(
        repository: CartRepository
    ): FetchCartUsecase = FetchCartUsecase(repository)

    @Provides
    @Singleton
    fun provideClearCartUseCase(repository: CartRepository): ClearCartUseCase =
        ClearCartUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteCartUseCase(repository: CartRepository): DeleteCartUseCase = DeleteCartUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateCartQuantityUseCase(repository: CartRepository): UpdateCartQuantityUseCase =
        UpdateCartQuantityUseCase(repository)
}
