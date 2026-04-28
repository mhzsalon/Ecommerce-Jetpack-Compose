package com.example.ecomm.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ecomm.SplashScreen
import com.example.ecomm.core.helper.DialogBox.AppDialogBox
import com.example.ecomm.features.auth.presentation.screens.LoginScreen
import com.example.ecomm.features.auth.presentation.screens.RegisterScreen
import com.example.ecomm.features.auth.presentation.viewmodel.AuthViewModel
import com.example.ecomm.features.products.presentation.screens.ProductDetailScreen
import com.example.ecomm.features.profile.presentation.screens.EditProfileScreen
import com.example.ecomm.features.profile.presentation.viewmodel.ProfileViewModel
import com.example.ecomm.features.shared.HomeTabScreen

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.SPLASH, builder = {
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                })
        }
        composable(Routes.HOME) { backStackEntry ->
            val homeEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.HOME)
            }
            val authViewModel: AuthViewModel = hiltViewModel(homeEntry)
            val profileViewModel: ProfileViewModel = hiltViewModel(homeEntry)

            HomeTabScreen(
                authViewModel = authViewModel,
                profileViewModel = profileViewModel,
                onOpenProduct = { id ->
                    navController.navigate(Routes.productDetail(id))
                },
                onOpenEditProfile = {
                    navController.navigate(Routes.EDIT_PROFILE)
                }
            )
        }
        composable(Routes.LOGIN) { backStackEntry ->
            val homeEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.HOME)
            }
            val authViewModel: AuthViewModel = hiltViewModel(homeEntry)

            LoginScreen(
                viewModel = authViewModel,
                onRedirect = {
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.REGISTER) { backStackEntry ->
            val homeEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.HOME)
            }
            val authViewModel: AuthViewModel = hiltViewModel(homeEntry)

            RegisterScreen(
                viewModel = authViewModel,
                onRedirect = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.popBackStack(Routes.LOGIN, inclusive = true)
                }

            )
        }
        composable(
            route = Routes.PRODUCT_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(Routes.PRODUCT_ID_ARG) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val homeEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.HOME)
            }
            val authViewModel: AuthViewModel = hiltViewModel(homeEntry)
            val id = backStackEntry.arguments?.getString(Routes.PRODUCT_ID_ARG).orEmpty()

            ProductDetailScreen(
                onBack = { navController.popBackStack() },
                id = id,
                authViewModel = authViewModel,
                onLoginRequired = { AppDialogBox.showLoginRequired() }
            )
        }

        composable(Routes.EDIT_PROFILE) { backStackEntry ->
            val homeEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.HOME)
            }
            val profileViewModel: ProfileViewModel = hiltViewModel(homeEntry)

            EditProfileScreen(viewModel = profileViewModel, onBack = {
                navController.popBackStack()
            })
        }

    })
}
