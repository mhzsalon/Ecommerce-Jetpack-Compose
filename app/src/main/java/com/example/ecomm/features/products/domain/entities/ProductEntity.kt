package com.example.ecomm.features.products.domain.entities

data class ProductEntity(
    val id: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val category: String,
    val price: Double,
    val rating: Double
)