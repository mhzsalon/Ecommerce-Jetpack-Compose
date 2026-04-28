package com.example.ecomm.core.network

import com.example.ecomm.features.products.data.model.ProductsResponse
import retrofit2.http.GET


// --- API Interface ---
interface RestApiClient {
    // Products
    @GET("products")
    suspend fun fetchProducts(): ProductsResponse
}