package com.example.ecomm.features.auth.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.auth.domain.repositories.AuthRepository

class LoginUsecase(private  val repository: AuthRepository) : UseCase<String, LoginParams>{
    override suspend fun invoke(params: LoginParams): Either<Failure, String> {
        return  repository.loginUser(params);
    }
}

data class LoginParams(
    val email: String,
    val password: String,
)