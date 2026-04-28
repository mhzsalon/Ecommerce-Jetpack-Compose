package com.example.ecomm.features.profile.domain.repositories

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.features.profile.domain.entities.UserEntity

interface ProfileRepository{
    suspend fun fetchProfile(userId: String): Either<Failure, UserEntity>
    suspend fun updateProfile(user: UserEntity): Either<Failure, Unit>
}