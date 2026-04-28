package com.example.ecomm.features.auth.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.ecomm.features.auth.data.repositories.AuthRepositoryImpl
import com.example.ecomm.features.auth.data.source.local.AuthLocalDataSource
import com.example.ecomm.features.auth.data.source.local.AuthLocalDataSourceImpl
import com.example.ecomm.features.auth.data.source.remote.AuthRemoteDataSource
import com.example.ecomm.features.auth.data.source.remote.AuthRemoteDataSourceImpl
import com.example.ecomm.features.auth.domain.repositories.AuthRepository
import com.example.ecomm.features.auth.domain.usecases.GetCachedUserUsecase
import com.example.ecomm.features.auth.domain.usecases.LoginUsecase
import com.example.ecomm.features.auth.domain.usecases.LogoutUserUseCase
import com.example.ecomm.features.auth.domain.usecases.RegisterUsecase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    //---------------------- Data Source ------------------
    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): AuthRemoteDataSource =
        AuthRemoteDataSourceImpl(firebaseAuth, firebaseFirestore)

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(
        dataStore: DataStore<Preferences>
    ): AuthLocalDataSource = AuthLocalDataSourceImpl(dataStore)

    //---------------------- Repository ------------------
    @Provides
    @Singleton
    fun provideAuthRepository(
        remoteDataSource: AuthRemoteDataSource,
        localDataSource: AuthLocalDataSource
    ): AuthRepository =
        AuthRepositoryImpl(remoteDataSource, localDataSource)

    //---------------------- Usecases ------------------
    @Provides
    @Singleton
    fun provideLoginUsecase(repository: AuthRepository): LoginUsecase = LoginUsecase(repository)

    @Provides
    @Singleton
    fun provideRegisterUsecase(repository: AuthRepository): RegisterUsecase =
        RegisterUsecase(repository)

    @Provides
    @Singleton
    fun provideCachedUserUsecase(repository: AuthRepository): GetCachedUserUsecase =
        GetCachedUserUsecase(repository)

    @Provides
    @Singleton
    fun provideLogoutUserUseCase(repository: AuthRepository): LogoutUserUseCase = LogoutUserUseCase(repository)

}