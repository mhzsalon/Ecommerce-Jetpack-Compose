package com.example.ecomm.core.usecase
import arrow.core.Either
import com.example.ecomm.core.error.Failure

interface UseCase<T, in Params> {
    suspend operator fun invoke(params: Params): Either<Failure, T>
}

// No params usecase
object NoParams