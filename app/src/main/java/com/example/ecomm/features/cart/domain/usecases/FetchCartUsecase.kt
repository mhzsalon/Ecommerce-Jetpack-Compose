package com.example.ecomm.features.cart.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.cart.domain.entities.CartEntity
import com.example.ecomm.features.cart.domain.repositories.CartRepository

class  FetchCartUsecase (private val repository: CartRepository): UseCase<List<CartEntity>, String>{
    override suspend fun invoke(params: String): Either<Failure, List<CartEntity>> {
        return  repository.fetchCartItems(params)
    }
}