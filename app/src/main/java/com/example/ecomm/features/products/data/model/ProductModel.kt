package com.example.ecomm.features.products.data.model

import com.example.ecomm.features.products.domain.entities.ProductEntity
import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("id")          val id: String,
    @SerializedName("title")       val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnail")   val thumbnail: String,
    @SerializedName("category")    val category: String,
    @SerializedName("price")       val price: Double,
    @SerializedName("rating")      val rating: Double
) {
    // Model -> Entity
    fun toEntity(): ProductEntity {
        return ProductEntity(
            id = id,
            title = title,
            description = description,
            thumbnail = thumbnail,
            category = category,
            price = price,
            rating = rating
        )
    }

    companion object {
        // Entity -> Model
        fun fromEntity(entity: ProductEntity): ProductModel {
            return ProductModel(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                thumbnail = entity.thumbnail,
                category = entity.category,
                price = entity.price,
                rating = entity.rating
            )
        }
    }
}

// --- Product response Data Model ---
data class ProductsResponse(
    @SerializedName("products") val products: List<ProductModel>
)
