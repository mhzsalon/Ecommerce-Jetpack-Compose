package com.example.ecomm.features.profile.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.NoParams
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.profile.domain.entities.UserEntity
import com.example.ecomm.features.profile.domain.repositories.ProfileRepository

class UpdateProfileUsecase(private val repository: ProfileRepository) : UseCase<Unit, UserEntity> {
    override suspend fun invoke(params: UserEntity): Either<Failure, Unit> {
        return repository.updateProfile(params);
    }
}