package com.example.ecomm.features.cart.data.model

import com.example.ecomm.features.cart.domain.entities.CartEntity
import com.example.ecomm.features.products.domain.entities.ProductEntity
import androidx.room.*

@Entity(tableName = "cart")
data class CartModel(
    @PrimaryKey
    @ColumnInfo(name = "id")          val id: String,
    @ColumnInfo(name = "user_id")     val userId: String,
    @ColumnInfo(name = "product_id")  val productId: String,
    @ColumnInfo(name = "title")       val title: String,
    @ColumnInfo(name = "thumbnail")   val thumbnail: String,
    @ColumnInfo(name = "price")       val price: Double,
    @ColumnInfo(name = "rating")       val rating: Double,
    @ColumnInfo(name = "quantity")    val quantity: Int,
    @ColumnInfo(name = "category")    val category: String,
    @ColumnInfo(name = "description")    val description: String,
    @ColumnInfo(name = "total_price") val totalPrice: Double,
    @ColumnInfo(name = "created_at")  val createdAt: Long  // epoch millis
) {

    fun toEntity(): CartEntity = CartEntity(
        id         = id,
        userId     = userId,
        product    = ProductEntity(
            id        = productId,
            title     = title,
            thumbnail = thumbnail,
            price     = price,
            category = category,
            description = description,
            rating = rating

        ),
        quantity   = quantity,
        totalPrice = totalPrice,
        createdAt  = createdAt
    )

    companion object {
        fun fromEntity(entity: CartEntity): CartModel = CartModel(
            id         = entity.id,
            userId     = entity.userId,
            productId  = entity.product.id,
            title      = entity.product.title,
            thumbnail  = entity.product.thumbnail,
            price      = entity.product.price,
            quantity   = entity.quantity,
            totalPrice = entity.totalPrice,
            rating = entity.product.rating,
            category = entity.product.category,
            description = entity.product.description,
            createdAt  = entity.createdAt
        )
    }
}