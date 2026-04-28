package com.example.ecomm.features.products.data.repositories

import android.util.Log.e
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.ecomm.core.error.Failure
import com.example.ecomm.core.error.ServerException
import com.example.ecomm.core.error.ServerFailure
import com.example.ecomm.features.products.data.source.remote.ProductRemoteDataSource
import com.example.ecomm.features.products.domain.entities.ProductEntity
import com.example.ecomm.features.products.domain.repositories.ProductRepository
import io.grpc.Server

class ProductRepositoryImpl(
    private val remoteSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun fetchProduct(): Either<Failure, List<ProductEntity>> {
        return try {
            val result = remoteSource.fetchProduct();
            result.map { it.toEntity() }.right()
        } catch (e: ServerException) {
            ServerFailure(e.message).left()
        }
    }
}