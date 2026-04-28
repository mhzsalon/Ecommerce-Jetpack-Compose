package com.example.ecomm.features.profile.di

import com.example.ecomm.core.network.RestApiClient
import com.example.ecomm.features.products.data.source.remote.ProductRemoteDataSource
import com.example.ecomm.features.products.data.source.remote.ProductRemoteDataSourceImpl
import com.example.ecomm.features.profile.data.remote_source.ProfileRemoteDataSource
import com.example.ecomm.features.profile.data.remote_source.ProfileRemoteDataSourceImpl
import com.example.ecomm.features.profile.data.repositories.ProfileRepositoryImpl
import com.example.ecomm.features.profile.domain.repositories.ProfileRepository
import com.example.ecomm.features.profile.domain.usecases.FetchProfileUseCase
import com.example.ecomm.features.profile.domain.usecases.UpdateProfileUsecase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    //---------------------- Data Source ------------------

    @Provides
    @Singleton
    fun providesProfileRemoteDataSource(firebaseFirestore: FirebaseFirestore): ProfileRemoteDataSource =
        ProfileRemoteDataSourceImpl(firebaseFirestore)

    //---------------------- Repository------------------
    @Provides
    @Singleton
    fun providesProfileRepository(remoteDataSource: ProfileRemoteDataSource): ProfileRepository =
        ProfileRepositoryImpl(remoteDataSource)

    //---------------------- Usecase ------------------
    @Provides
    @Singleton
    fun provideFetchProfileUsecase(repository: ProfileRepository): FetchProfileUseCase =
        FetchProfileUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateProfileUsecase(repository: ProfileRepository): UpdateProfileUsecase =
        UpdateProfileUsecase(repository)
}