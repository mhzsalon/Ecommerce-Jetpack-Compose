package com.example.ecomm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ecomm.core.helper.Notification.NotificationHelper
import com.example.ecomm.core.theme.AppColors
import com.example.ecomm.features.auth.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: ()-> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    // Request notification permission
    NotificationHelper.RequestPermission(
        onGranted = { },
        onDenied  = { }
    )

    LaunchedEffect(Unit) {
        authViewModel.onGetCachedUser()

        // navigates to home screen after the delay
        delay(2000)
        onNavigateToHome()
    }
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues).background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFAEF),
                            AppColors.scaffoldBackground,
                            Color(0xFFF9EED9),
                        )
                    )
                ),
            contentAlignment = Alignment.Center

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.app_icon),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Ecomm",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.W800,
                        color = AppColors.textPrimary
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "Shop smarter, faster, better.",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp, color = AppColors.textPrimary)
                )
            }
        }
    }
}
