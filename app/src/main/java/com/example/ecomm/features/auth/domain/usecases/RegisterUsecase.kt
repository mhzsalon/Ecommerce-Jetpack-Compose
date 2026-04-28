package com.example.ecomm.features.auth.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.auth.domain.repositories.AuthRepository

class RegisterUsecase(private val repository: AuthRepository): UseCase<String, RegisterParams>{
    override suspend fun invoke(params: RegisterParams): Either<Failure, String> {
      return  repository.registerUser(params);
    }
}

data class RegisterParams(
    val fullName: String,
    val email: String,
    val password: String,
)