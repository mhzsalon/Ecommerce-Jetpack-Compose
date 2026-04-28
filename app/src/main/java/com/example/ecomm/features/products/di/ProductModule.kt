package com.example.ecomm.features.products.di

import com.example.ecomm.core.network.RestApiClient
import com.example.ecomm.features.products.data.repositories.ProductRepositoryImpl
import com.example.ecomm.features.products.data.source.remote.ProductRemoteDataSource
import com.example.ecomm.features.products.data.source.remote.ProductRemoteDataSourceImpl
import com.example.ecomm.features.products.domain.repositories.ProductRepository
import com.example.ecomm.features.products.domain.usecases.FetchProductUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

    //---------------------- Data Source ------------------
    @Provides
    @Singleton
    fun provideProductRemoteDataSource(restApiClient: RestApiClient): ProductRemoteDataSource =
        ProductRemoteDataSourceImpl(restApiClient)

    //---------------------- Repository ------------------
    @Provides
    @Singleton
    fun provideProductRepository(remoteDataSource: ProductRemoteDataSource): ProductRepository =
        ProductRepositoryImpl(remoteDataSource)

    //---------------------- Usecase ------------------
    @Provides
    @Singleton
    fun provideFetchProductUsecase(repository: ProductRepository): FetchProductUsecase =
        FetchProductUsecase(repository)
}