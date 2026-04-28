package com.example.ecomm.features.products.data.source.remote

import com.example.ecomm.core.error.ServerException
import com.example.ecomm.core.network.RestApiClient
import com.example.ecomm.features.products.data.model.ProductModel
import retrofit2.HttpException

interface ProductRemoteDataSource {
    suspend fun fetchProduct(): List<ProductModel>
}


class ProductRemoteDataSourceImpl(private val restApiClient: RestApiClient) :
    ProductRemoteDataSource {
    override suspend fun fetchProduct(): List<ProductModel> {
        try {
            return restApiClient.fetchProducts().products
        } catch (e: HttpException) {
            throw ServerException("Failed to fetch products: ${e.code()}")
        } catch (e: Exception) {
            throw ServerException(e.message ?: "Unknown error")
        }
    }
}