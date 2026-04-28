package com.example.ecomm.features.cart.presentation.viewmodel.state

import com.example.ecomm.core.error.Failure
import com.example.ecomm.features.cart.domain.entities.CartEntity

data class CartState(
    val addToCartState: AddToCartState = AddToCartState.Initial,
    val fetchCartState: FetchCartState = FetchCartState.Initial,
    val deleteCartState: DeleteCartState = DeleteCartState.Initial,
    val cartCheckoutState: CartCheckoutState = CartCheckoutState.Initial
)


sealed class AddToCartState{
    object Initial : AddToCartState()
    object Loading : AddToCartState()
    object Success : AddToCartState()

    data class Error (val failure: Failure): AddToCartState()
}

sealed class FetchCartState{
    object  Initial : FetchCartState()
    object Loading: FetchCartState()

    data class Success(val cartList: List<CartEntity>): FetchCartState()

    data class Error(val failure: Failure): FetchCartState()
}

sealed class DeleteCartState{
    object Initial : DeleteCartState()
    object Loading: DeleteCartState()
    object Success : DeleteCartState()

    data class Error(val failure:Failure) : DeleteCartState()
}

sealed class CartCheckoutState(){
    object  Initial : CartCheckoutState()
    object  Loading : CartCheckoutState()
    object  Success : CartCheckoutState()
    data class  Error(val failure : Failure) : CartCheckoutState()
}