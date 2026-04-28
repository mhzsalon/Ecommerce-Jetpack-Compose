package com.example.ecomm.features.shared
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecomm.core.components.AppScaffold
import com.example.ecomm.core.helper.DialogBox.AppDialogBox
import com.example.ecomm.core.theme.AppColors
import com.example.ecomm.features.auth.presentation.viewmodel.AuthViewModel
import com.example.ecomm.features.profile.presentation.viewmodel.ProfileViewModel
import com.example.ecomm.features.shared.navHost.HomeHostname
@Composable
fun HomeTabScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel,
    onOpenProduct: (String) -> Unit,
    onOpenEditProfile: () -> Unit

) {
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val tabs = HomeTab.items

    val currentRoute = navController
        .currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route

    fun requireAuthOrNavigate(route: String) {
        if (authState.cachedUserId == null && route != HomeTab.Home.route) {
            AppDialogBox.showLoginRequired()
        } else {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
            }
        }
    }

    AppScaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.height(75.dp)
            ) {
                tabs.forEach { tab ->

                    val selected = currentRoute == tab.route

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            requireAuthOrNavigate(tab.route)
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title
                            )
                        },
                        label = {
                            Text(tab.title)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AppColors.primary,
                            selectedTextColor = AppColors.primary,
                            unselectedIconColor = AppColors.iconMuted,
                            unselectedTextColor = AppColors.iconMuted,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HomeHostname(
                navController = navController,
                onOpenProduct = onOpenProduct,
                authViewModel = authViewModel,
                profileViewModel = profileViewModel,
                onOpenEditProfile = onOpenEditProfile
            )
        }
    }
}