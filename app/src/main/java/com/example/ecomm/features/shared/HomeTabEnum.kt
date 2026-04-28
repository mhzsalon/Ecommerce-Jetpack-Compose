package com.example.ecomm.features.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ecomm.features.cart.presentation.screens.CartScreen
import com.example.ecomm.features.products.presentation.screens.ProductScreen
import com.example.ecomm.features.profile.presentation.screens.ProfileScreen
sealed class HomeTab(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    object Home : HomeTab("Home", Icons.Default.Home, "product")
    object Cart : HomeTab("Cart", Icons.Default.ShoppingCart, "cart")
    object Profile : HomeTab("Profile", Icons.Default.Person, "profile")

    companion object {
        val items = listOf(Home, Cart, Profile)
    }
}