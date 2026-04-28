package com.example.ecomm.features.cart.data.repositories

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.ecomm.core.error.CacheException
import com.example.ecomm.core.error.CacheFailure
import com.example.ecomm.core.error.Failure
import com.example.ecomm.features.cart.data.model.CartModel
import com.example.ecomm.features.cart.data.source.local.CartLocalDataSource
import com.example.ecomm.features.cart.domain.entities.CartEntity
import com.example.ecomm.features.cart.domain.repositories.CartRepository
import com.example.ecomm.features.cart.domain.usecases.UpdateQuantityParams

class CartRepositoryImpl(
    private val localDataSource: CartLocalDataSource
) : CartRepository {

    override suspend fun fetchCartItems(userId: String): Either<Failure, List<CartEntity>> {
        return try {
            val cartList = localDataSource.fetchCartItems(userId)
                .map { it.toEntity() }
            cartList.right()
        } catch (e: CacheException) {
            CacheFailure(e.message).left()
        }
    }

    override suspend fun addToCart(entity: CartEntity): Either<Failure, Unit> {
        return try {
            localDataSource.addToCart(CartModel.fromEntity(entity))
            Unit.right()
        } catch (e: CacheException) {
            CacheFailure(e.message).left()
        }
    }

    override suspend fun deleteItem(id: String): Either<Failure, Unit> {
        return try {
            localDataSource.deleteItem(id)
            Unit.right()
        } catch (e: CacheException) {
            CacheFailure(e.message).left()
        }
    }

//    override suspend fun updateCartItem(entity: CartEntity) =
//        localDataSource.updateCartItem(CartModel.fromEntity(entity))

    override suspend fun clearCart(userId: String): Either<Failure, Unit> {
        return try {
            localDataSource.clearCart(userId)
            Unit.right()
        } catch (e: CacheException) {
            CacheFailure(e.message).left()
        }
    }

    override suspend fun updateCartItemQuantity(params: UpdateQuantityParams): Either<Failure, Unit> {
        return try {
            localDataSource.updateCartItemQuantity(id = params.id, quantity = params.quantity, total = params.totalPrice)
            Unit.right()
        }catch (e: CacheException){
            CacheFailure(e.message).left()
        }
    }
}