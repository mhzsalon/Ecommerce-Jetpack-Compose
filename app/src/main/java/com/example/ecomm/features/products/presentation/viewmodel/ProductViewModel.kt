package com.example.ecomm.features.products.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomm.features.products.domain.usecases.FetchProductUsecase
import com.example.ecomm.features.products.presentation.viewmodel.state.FetchProductState
import com.example.ecomm.features.products.presentation.viewmodel.state.ProductState
import com.google.firebase.firestore.util.Assert.fail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val fetchProductUsecase: FetchProductUsecase
) : ViewModel() {
    private val _productState = MutableStateFlow(ProductState())
    val productState = _productState.asStateFlow()

    fun onFetchProduct() {
        viewModelScope.launch {
            _productState.update { it.copy(fetchProductState = FetchProductState.Loading) }

            fetchProductUsecase(Unit).onRight { productList ->
                _productState.update {
                    it.copy(
                        allProducts = productList,
                        fetchProductState = FetchProductState.Success(
                            productList
                        )
                    )
                }
            }.onLeft { failure ->
                _productState.update { it.copy(fetchProductState = FetchProductState.Error(failure)) }
            }
        }
    }

    // Filter from original list
    fun onSearchQueryChange(query: String) {
        _productState.update { it.copy(searchQuery = query) }

        val filteredList = if (query.length < 3) {
            _productState.value.allProducts
        } else {
            _productState.value.allProducts.filter { product ->
                product.title.contains(query, ignoreCase = true)
            }
        }

        _productState.update {
            it.copy(fetchProductState = FetchProductState.Success(filteredList))
        }
    }

}