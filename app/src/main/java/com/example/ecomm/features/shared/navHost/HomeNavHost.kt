package com.example.ecomm.features.shared.navHost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecomm.features.auth.presentation.viewmodel.AuthViewModel
import com.example.ecomm.features.cart.presentation.screens.CartScreen
import com.example.ecomm.features.products.presentation.screens.ProductScreen
import com.example.ecomm.features.profile.presentation.screens.ProfileScreen
import com.example.ecomm.features.profile.presentation.viewmodel.ProfileViewModel
import com.example.ecomm.features.shared.HomeTab

@Composable
fun HomeHostname(
    navController: NavHostController,
    onOpenProduct: (String) -> Unit,
    authViewModel: AuthViewModel,
    onOpenEditProfile: () -> Unit,
    profileViewModel: ProfileViewModel
){
    NavHost(
        navController = navController,
        startDestination = HomeTab.Home.route,
    ) {

        composable(HomeTab.Home.route) {
            ProductScreen(
                onNavigateTo = { id ->
                    onOpenProduct(id)
                }
            )
        }
        composable(HomeTab.Cart.route) {
            CartScreen()
        }

        composable(HomeTab.Profile.route) {
            ProfileScreen(
                authViewModel = authViewModel,
                viewModel = profileViewModel,
                onLogoutSuccess = {
                    navController.navigate(HomeTab.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onOpenEditProfile = {
                    onOpenEditProfile()
                }

            )
        }


    }
}
