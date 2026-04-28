package com.example.ecomm.features.products.presentation.viewmodel.state

import com.example.ecomm.core.error.Failure
import com.example.ecomm.features.products.domain.entities.ProductEntity

data class ProductState(
    val searchQuery: String = "",
    val allProducts: List<ProductEntity> = emptyList(),
    val fetchProductState: FetchProductState = FetchProductState.Initial
)

sealed class FetchProductState {
    object Initial : FetchProductState()

    object Loading : FetchProductState()

    data class Success(val productList: List<ProductEntity>) : FetchProductState()

    data class Error(val failure: Failure) : FetchProductState()
}


