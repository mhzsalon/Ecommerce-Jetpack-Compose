package com.example.ecomm.features.cart.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.cart.domain.repositories.CartRepository

class UpdateCartQuantityUseCase(
    private  val repository: CartRepository
) : UseCase<Unit, UpdateQuantityParams>{
    override suspend fun invoke(params: UpdateQuantityParams): Either<Failure, Unit> {
       return  repository.updateCartItemQuantity(params)
    }
}

data class UpdateQuantityParams(
    val quantity: Int,
    val id: String,
    val totalPrice: Double
)