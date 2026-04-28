package com.example.ecomm.features.cart.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ecomm.features.cart.data.model.CartModel

@Dao
interface CartDao {

    @Query("SELECT * FROM cart WHERE user_id = :userId")
    suspend fun getCartItems(userId: String): List<CartModel>

    @Query("SELECT * FROM cart WHERE id = :cartId")
    suspend fun getCartItem(cartId: String): CartModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartModel)

    @Update
    suspend fun updateCartItem(item: CartModel)

    @Query("UPDATE cart SET quantity = :quantity, total_price = :totalPrice WHERE id = :id")
    suspend fun updateCartItemQuantity(id: String, quantity: Int, totalPrice: Double)

    @Query("DELETE FROM cart WHERE id = :cartId")
    suspend fun deleteCartItemById(cartId: String)

    @Query("DELETE FROM cart WHERE user_id = :userId")
    suspend fun clearCart(userId: String)
}