package com.example.ecomm.features.cart.data.source.local

import com.example.ecomm.features.cart.data.model.CartModel


interface CartLocalDataSource {
    suspend fun fetchCartItems(userId: String): List<CartModel>
    suspend fun addToCart(item: CartModel)
    suspend fun deleteItem(id: String)
    suspend fun updateCartItemQuantity(id: String, quantity:Int, total: Double)
    suspend fun clearCart(userId: String)
}

class CartLocalDataSourceImpl(
    private val cartDao: CartDao
) : CartLocalDataSource {

    override suspend fun fetchCartItems(userId: String): List<CartModel> =
        cartDao.getCartItems(userId)

    override suspend fun addToCart(item: CartModel) =
        cartDao.insertCartItem(item)

    override suspend fun deleteItem(id: String) =
        cartDao.deleteCartItemById(id)

    override suspend fun updateCartItemQuantity(id: String, quantity:Int, total: Double) =
        cartDao.updateCartItemQuantity(id = id, quantity=quantity, totalPrice = total)

    override suspend fun clearCart(userId: String) =
        cartDao.clearCart(userId)
}
