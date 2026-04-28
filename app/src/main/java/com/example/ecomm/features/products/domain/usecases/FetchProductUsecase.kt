package com.example.ecomm.features.products.domain.usecases

import arrow.core.Either
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.usecase.UseCase
import com.example.ecomm.features.products.domain.entities.ProductEntity
import com.example.ecomm.features.products.domain.repositories.ProductRepository

class FetchProductUsecase (private val repository: ProductRepository) : UseCase<List<ProductEntity>, Unit>{
    override suspend fun invoke(params: Unit): Either<Failure, List<ProductEntity>> {
      return  repository.fetchProduct()
    }
}