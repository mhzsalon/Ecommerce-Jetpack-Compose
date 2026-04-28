package com.example.ecomm.features.auth.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.auth.domain.repositories.AuthRepository

class LogoutUserUseCase(private val  repository: AuthRepository) : UseCase<Unit, Unit>{
    override suspend fun invoke(params: Unit): Either<Failure, Unit> {
        return  repository.logoutUser()
    }
}