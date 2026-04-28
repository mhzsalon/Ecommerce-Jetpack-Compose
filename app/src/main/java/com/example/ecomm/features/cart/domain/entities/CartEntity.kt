package com.example.ecomm.features.cart.domain.entities

import com.example.ecomm.features.products.domain.entities.ProductEntity

data class CartEntity (
    val id: String,
    val userId: String,
    val product: ProductEntity,
    val quantity: Int,
    val totalPrice : Double,
    val createdAt: Long
){
    fun copyWith(
        id: String = this.id,
        userId: String = this.userId,
        product: ProductEntity = this.product,
        quantity: Int = this.quantity,
        totalPrice: Double = this.totalPrice,
        createdAt: Long = this.createdAt
    ) = copy(
        id = id,
        userId = userId,
        product = product,
        quantity = quantity,
        totalPrice = totalPrice,
        createdAt = createdAt
    )
}
