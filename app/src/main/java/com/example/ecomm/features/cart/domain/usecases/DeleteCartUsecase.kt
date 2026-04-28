package com.example.ecomm.features.cart.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.cart.domain.repositories.CartRepository

class DeleteCartUseCase(private val repository: CartRepository) : UseCase<Unit, String> {
    override suspend fun invoke(params: String): Either<Failure, Unit> {
        return repository.deleteItem(params)
    }
}