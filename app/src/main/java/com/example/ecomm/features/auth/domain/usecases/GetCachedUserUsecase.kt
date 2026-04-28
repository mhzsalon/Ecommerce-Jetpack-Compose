package com.example.ecomm.features.auth.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.auth.domain.repositories.AuthRepository

class GetCachedUserUsecase(private  val repository: AuthRepository) : UseCase<String?, Unit>{
    override suspend fun invoke(params: Unit): Either<Failure, String?> {
        return  repository.getCachedUser();
    }
}