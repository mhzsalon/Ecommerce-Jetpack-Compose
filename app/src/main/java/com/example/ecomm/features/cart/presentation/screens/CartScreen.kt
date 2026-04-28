package com.example.ecomm.features.cart.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecomm.core.components.EmptyState
import com.example.ecomm.core.components.ErrorState
import com.example.ecomm.core.components.LoadingState
import com.example.ecomm.core.helper.DialogBox.AppDialogBox
import com.example.ecomm.core.helper.Notification.NotificationHelper
import com.example.ecomm.core.theme.AppColors
import com.example.ecomm.features.cart.presentation.components.CartCheckoutSection
import com.example.ecomm.features.cart.presentation.components.CartItemCard
import com.example.ecomm.features.cart.presentation.viewmodel.CartEvent
import com.example.ecomm.features.cart.presentation.viewmodel.CartViewModel
import com.example.ecomm.features.cart.presentation.viewmodel.state.CartCheckoutState
import com.example.ecomm.features.cart.presentation.viewmodel.state.FetchCartState

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel()
) {
    val cartState by viewModel.cartState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchCart()
        viewModel.events.collect { event ->
            when (event) {
                CartEvent.ItemRemoved -> {
                    AppDialogBox.success("Product removed from the cart")
                }

                is CartEvent.RemoveItemError -> {
                    AppDialogBox.error(event.message)
                }

                else -> Unit
            }

            when (event) {
                CartEvent.CheckoutSuccess -> {
                    AppDialogBox.success("Order placed successfully")
                    NotificationHelper.show(
                        context = context,
                        title = "Order Placed",
                        message = "Your order has been confirmed"
                    )
                }
                is CartEvent.CheckoutError -> {
                    AppDialogBox.error(event.message)
                }
                else -> Unit
            }
        }

    }




    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //  HEADER section ----------------------------
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 10.dp, end = 10.dp)
        ) {
            Text(
                "Cart",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.W800
                )
            )
            TextButton(onClick = {
                viewModel.clearCart()
            }) {
                Text(
                    "Clear",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AppColors.danger,
                        fontWeight = FontWeight.W600
                    )
                )
            }
        }

        // BODY section -----------------------
        when (val fetchState = cartState.fetchCartState) {
            is FetchCartState.Loading -> {
                LoadingState()
            }

            is FetchCartState.Success -> {
                if (fetchState.cartList.isEmpty())
                    EmptyState(
                        icon = Icons.Default.Info,
                        title = "Your cart is empty",
                        description = "Add products to your bag and they will show up here for quick checkout.",
                    )
                else {
                    val cart = fetchState.cartList
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        items(cart.count()) { index ->
                            val cartItem = cart[index]
                            CartItemCard(
                                cartItem = cartItem,
                                onDecrement = {
                                    viewModel.updateCartItemQuantity(
                                        cartEntity = cartItem,
                                        newQuantity = cartItem.quantity - 1
                                    )
                                },
                                onIncrement = {
                                    viewModel.updateCartItemQuantity(
                                        cartEntity = cartItem,
                                        newQuantity = cartItem.quantity + 1
                                    )

                                },
                                onDelete = { viewModel.deleteCartItem(cartItem.id) },
                            )
                        }
                    }
                    //  BOTTOM section------------------------
                    CartCheckoutSection(
                        total = cart.fold(0.0) { sum, item ->
                            sum + item.totalPrice
                        },
                        itemCount = cart.count(),
                        isLoading = cartState.cartCheckoutState is CartCheckoutState.Loading,
                        onCheckout = {viewModel.onCartCheckout()}
                    )
                }
            }

            is FetchCartState.Error -> {
                ErrorState(
                    title = "Unable to load cart",
                    message = fetchState.failure.message,
                    onRetry = {
                        viewModel.fetchCart()
                    })
            }

            else -> Unit
        }

    }
}
