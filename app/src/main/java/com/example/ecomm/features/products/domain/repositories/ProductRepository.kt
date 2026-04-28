package com.example.ecomm.features.products.domain.repositories

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.features.products.domain.entities.ProductEntity

interface  ProductRepository{
    suspend fun  fetchProduct() : Either<Failure, List<ProductEntity>>
}