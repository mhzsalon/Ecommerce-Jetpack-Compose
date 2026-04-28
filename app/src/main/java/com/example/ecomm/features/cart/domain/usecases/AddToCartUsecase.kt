package com.example.ecomm.features.cart.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.cart.domain.entities.CartEntity
import com.example.ecomm.features.cart.domain.repositories.CartRepository
import com.example.ecomm.features.products.domain.entities.ProductEntity
import java.util.UUID

class AddToCartUsecase (
    private  val repository: CartRepository
) : UseCase<Unit, CartParam>{
    override suspend fun invoke(params: CartParam): Either<Failure, Unit> {
        val cartEntity = CartEntity(
            id = UUID.randomUUID().toString(),
            product = params.productEntity,
            quantity = params.quantity,
            totalPrice = params.quantity * params.productEntity.price,
            createdAt = System.currentTimeMillis(),
            userId = params.userId,
        )
        return  repository.addToCart(cartEntity)
    }
}



data class CartParam(
    val productEntity: ProductEntity,
    val quantity: Int,
    val userId: String
)