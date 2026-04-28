package com.example.ecomm.features.cart.domain.repositories

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.features.cart.domain.entities.CartEntity

import com.example.ecomm.features.cart.domain.usecases.UpdateQuantityParams

interface CartRepository {
    suspend fun fetchCartItems(userId: String): Either<Failure, List<CartEntity>>
    suspend fun addToCart(entity: CartEntity): Either<Failure, Unit>
    suspend fun deleteItem(id: String): Either<Failure, Unit>
    suspend fun clearCart(userId: String) :  Either<Failure, Unit>
    suspend fun  updateCartItemQuantity(params: UpdateQuantityParams) : Either<Failure, Unit>
}
