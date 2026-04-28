package com.example.ecomm.features.profile.data.repositories

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.error.ServerException
import com.example.ecomm.core.error.ServerFailure
import com.example.ecomm.features.profile.data.model.UserModel
import com.example.ecomm.features.profile.data.remote_source.ProfileRemoteDataSource
import com.example.ecomm.features.profile.domain.entities.UserEntity
import com.example.ecomm.features.profile.domain.repositories.ProfileRepository

class ProfileRepositoryImpl(private val remoteSource: ProfileRemoteDataSource) : ProfileRepository {
    override suspend fun fetchProfile(userId: String): Either<Failure, UserEntity> {
        return try {
            val userModel = remoteSource.fetchProfile(userId)
            userModel.toEntity().right()

        } catch (e: ServerException) {
            Log.d("TAG", e.message)
            ServerFailure(e.message).left()
        }
    }

    override suspend fun updateProfile(user: UserEntity): Either<Failure, Unit> {
        return try {
            remoteSource.updateProfile(UserModel.fromEntity(user))
            Unit.right()
        } catch (e: ServerException) {
            ServerFailure(e.message).left()
        }
    }
}