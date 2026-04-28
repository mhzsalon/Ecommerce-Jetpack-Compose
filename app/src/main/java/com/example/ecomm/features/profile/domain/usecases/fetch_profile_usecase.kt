package com.example.ecomm.features.profile.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.profile.domain.entities.UserEntity
import com.example.ecomm.features.profile.domain.repositories.ProfileRepository

class FetchProfileUseCase(private val repository: ProfileRepository) : UseCase<UserEntity, String> {
    override suspend fun invoke(params: String): Either<Failure, UserEntity> {
        return repository.fetchProfile(params)
    }
}