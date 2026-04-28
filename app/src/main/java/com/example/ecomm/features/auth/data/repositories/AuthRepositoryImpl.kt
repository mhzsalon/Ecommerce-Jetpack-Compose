package com.example.ecomm.features.auth.data.repositories

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.ecomm.core.error.AuthException
import com.example.ecomm.core.error.AuthFailure
import com.example.ecomm.core.error.CacheException
import com.example.ecomm.core.error.CacheFailure
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.error.ServerException
import com.example.ecomm.core.error.ServerFailure
import com.example.ecomm.features.auth.data.source.local.AuthLocalDataSource
import com.example.ecomm.features.auth.data.source.remote.AuthRemoteDataSource
import com.example.ecomm.features.auth.domain.repositories.AuthRepository
import com.example.ecomm.features.auth.domain.usecases.LoginParams
import com.example.ecomm.features.auth.domain.usecases.RegisterParams

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) : AuthRepository {
    override suspend fun loginUser(loginParams: LoginParams): Either<Failure, String> {
        return try {
            val userId = remoteDataSource.loginUser(loginParams)
            localDataSource.cacheUserId(userId)
            userId.right()
        } catch (e: AuthException) {
            AuthFailure(e.message).left()
        } catch (e: ServerException) {
            ServerFailure(e.message).left()
        } catch (e: CacheException) {
            CacheFailure(e.message).left()
        }
    }

    override suspend fun registerUser(registerParams: RegisterParams): Either<Failure, String> {
        return try {
            val userId = remoteDataSource.registerUser(registerParams)
            localDataSource.cacheUserId(userId)
            userId.right()
        } catch (e: AuthException) {
            AuthFailure(e.message).left()
        } catch (e: ServerException) {
            ServerFailure(e.message).left()
        } catch (e: CacheException) {
            CacheFailure(e.message).left()
        }
    }

    override suspend fun getCachedUser(): Either<Failure, String?> {
        return try {
            val userId = localDataSource.getCachedUserId()
            userId.right()
        } catch (e: CacheException) {
            CacheFailure(e.message).left()
        }
    }

    override suspend fun logoutUser(): Either<Failure, Unit> {
        return try {
            remoteDataSource.logoutUser()
            localDataSource.deleteCachedUserId()
            Unit.right()
        }catch (e: ServerException){
            ServerFailure(e.message).left()
        }catch (e: CacheException){
            CacheFailure(e.message).left()
        }
    }

}