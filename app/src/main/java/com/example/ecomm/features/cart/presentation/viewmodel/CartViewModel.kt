package com.example.ecomm.features.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomm.core.error.CacheFailure
import com.example.ecomm.features.auth.domain.usecases.GetCachedUserUsecase
import com.example.ecomm.features.cart.domain.entities.CartEntity
import com.example.ecomm.features.cart.domain.usecases.AddToCartUsecase
import com.example.ecomm.features.cart.domain.usecases.CartParam
import com.example.ecomm.features.cart.domain.usecases.ClearCartUseCase
import com.example.ecomm.features.cart.domain.usecases.DeleteCartUseCase
import com.example.ecomm.features.cart.domain.usecases.FetchCartUsecase
import com.example.ecomm.features.cart.domain.usecases.UpdateCartQuantityUseCase
import com.example.ecomm.features.cart.domain.usecases.UpdateQuantityParams
import com.example.ecomm.features.cart.presentation.viewmodel.state.AddToCartState
import com.example.ecomm.features.cart.presentation.viewmodel.state.CartCheckoutState
import com.example.ecomm.features.cart.presentation.viewmodel.state.CartState
import com.example.ecomm.features.cart.presentation.viewmodel.state.DeleteCartState
import com.example.ecomm.features.cart.presentation.viewmodel.state.FetchCartState
import com.example.ecomm.features.products.domain.entities.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CartEvent {
    data object AddToCartSuccess : CartEvent
    data class AddToCartError(val message: String) : CartEvent
    data object ItemRemoved : CartEvent
    data class RemoveItemError(val message: String) : CartEvent
    data object CheckoutSuccess : CartEvent
    data class CheckoutError(val message: String) : CartEvent
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addToCartUsecase: AddToCartUsecase,
    private val fetchCartUsecase: FetchCartUsecase,
    private val getCachedUserUsecase: GetCachedUserUsecase,
    private val clearCartUseCase: ClearCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase
) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()

    private val _events = MutableSharedFlow<CartEvent>()
    val events = _events.asSharedFlow()

    private var userId: String? = null

    private var quantityUpdateJob: Job? = null

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            refreshUserId(
                onError = { failure ->
                    _cartState.update {
                        it.copy(fetchCartState = FetchCartState.Error(failure))
                    }
                }
            )
            fetchCart()
        }
    }

    private suspend fun refreshUserId(
        onError: ((com.example.ecomm.core.error.Failure) -> Unit)? = null
    ): String? {
        return getCachedUserUsecase(Unit).fold(
            ifLeft = { failure ->
                onError?.invoke(failure)
                userId = null
                null
            },
            ifRight = { id ->
                userId = id
                id
            }
        )
    }

    fun addToCart(productEntity: ProductEntity, quantity: Int) {
        viewModelScope.launch {
            val id = refreshUserId()
            if (id.isNullOrBlank()) {
                _cartState.update {
                    it.copy(addToCartState = AddToCartState.Initial)
                }
                _events.emit(CartEvent.AddToCartError("User not logged in"))
                return@launch
            }

            _cartState.update { it.copy(addToCartState = AddToCartState.Loading) }
            addToCartUsecase(
                CartParam(
                    productEntity = productEntity,
                    quantity = quantity,
                    userId = id
                )
            ).fold(
                ifLeft = { failure ->
                    _cartState.update { it.copy(addToCartState = AddToCartState.Initial) }
                    _events.emit(CartEvent.AddToCartError(failure.message))
                },
                ifRight = {
                    _cartState.update { it.copy(addToCartState = AddToCartState.Initial) }
                    _events.emit(CartEvent.AddToCartSuccess)
                }
            )
        }
    }

    fun fetchCart() {
        viewModelScope.launch {
            val id = refreshUserId(
                onError = { failure ->
                    _cartState.update { it.copy(fetchCartState = FetchCartState.Error(failure)) }
                }
            )
            if (id.isNullOrBlank()) {
                _cartState.update { it.copy(fetchCartState = FetchCartState.Success(emptyList())) }
                return@launch
            }

            _cartState.update { it.copy(fetchCartState = FetchCartState.Loading) }
            fetchCartUsecase(id).fold(
                ifLeft = { failure ->
                    _cartState.update { it.copy(fetchCartState = FetchCartState.Error(failure)) }
                },
                ifRight = { cartList ->
                    _cartState.update { it.copy(fetchCartState = FetchCartState.Success(cartList)) }
                }
            )
        }
    }


    fun clearCart() {
        viewModelScope.launch {
            val id = refreshUserId() ?: return@launch
            clearCartUseCase(id).onRight {
                _cartState.update {
                    it.copy(fetchCartState = FetchCartState.Success(emptyList()))
                }
            }
        }
    }

    fun deleteCartItem(id: String) {
        viewModelScope.launch {
            _cartState.update { it.copy(deleteCartState = DeleteCartState.Loading) }

            deleteCartUseCase(id).onRight {
                _cartState.update { currentState ->
                    val updatedFetchState = when (val fetchState = currentState.fetchCartState) {
                        is FetchCartState.Success -> FetchCartState.Success(
                            fetchState.cartList.filter { it.id != id }  // ← remove deleted item
                        )

                        else -> fetchState  // leave other states untouched
                    }
                    currentState.copy(
                        deleteCartState = DeleteCartState.Initial,
                        fetchCartState = updatedFetchState
                    )
                }
                _events.emit(CartEvent.ItemRemoved)
            }.onLeft { failure ->
                _cartState.update { it.copy(deleteCartState = DeleteCartState.Initial) }
                _events.emit(CartEvent.RemoveItemError(failure.message))

            }
        }
    }

    fun updateCartItemQuantity(cartEntity: CartEntity, newQuantity: Int) {
        if (newQuantity < 1) return

        // holds the previous state for fallback
        val previousState = _cartState.value.fetchCartState

        // optimistic ui update
        _cartState.update { currentState ->
            val updatedFetchState = when (val fetchState = currentState.fetchCartState) {
                is FetchCartState.Success -> FetchCartState.Success(
                    fetchState.cartList.map {
                        if (it.id == cartEntity.id)
                            it.copyWith(
                                quantity = newQuantity,
                                totalPrice = newQuantity * it.product.price
                            )
                        else it
                    }
                )

                else -> fetchState
            }
            currentState.copy(fetchCartState = updatedFetchState)
        }

        //debouncing DB writes
        // Cancel any pending DB write from previous taps
        quantityUpdateJob?.cancel()

        quantityUpdateJob = viewModelScope.launch {
            // Wait 500ms after the last tap before writing to DB
            delay(500)
            updateCartQuantityUseCase(
                UpdateQuantityParams(
                    id = cartEntity.id,
                    quantity = newQuantity,
                    totalPrice = newQuantity * cartEntity.product.price
                )
            ).onLeft { failure ->
                _cartState.update {
                    it.copy(
                        fetchCartState = previousState,   // revert UI to previous state on failure
                        deleteCartState = DeleteCartState.Initial
                    )
                }
                _events.emit(CartEvent.RemoveItemError(failure.message))
            }
        }
    }

    fun onCartCheckout() {
        viewModelScope.launch {
            val id = refreshUserId() ?: return@launch
            _cartState.update {
                it.copy(cartCheckoutState = CartCheckoutState.Loading)
            }
            clearCartUseCase(id).onRight {
                _cartState.update {
                    it.copy(
                        cartCheckoutState = CartCheckoutState.Initial,
                        fetchCartState = FetchCartState.Success(emptyList())
                    )
                }
                _events.emit(CartEvent.CheckoutSuccess)
            }.onLeft { failure ->
                _cartState.update {
                    it.copy(cartCheckoutState = CartCheckoutState.Initial)
                }
                _events.emit(CartEvent.CheckoutError(failure.message))
            }
        }
    }
}
