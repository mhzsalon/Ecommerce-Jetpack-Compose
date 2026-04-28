package com.example.ecomm.features.auth.domain.repositories

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.features.auth.domain.usecases.LoginParams
import com.example.ecomm.features.auth.domain.usecases.RegisterParams

interface  AuthRepository{
    suspend fun  loginUser (loginParams: LoginParams) : Either<Failure, String>
    suspend fun  registerUser (registerParams: RegisterParams) : Either<Failure, String>
    suspend fun  getCachedUser () : Either<Failure, String?>

    suspend fun logoutUser(): Either<Failure, Unit>
}