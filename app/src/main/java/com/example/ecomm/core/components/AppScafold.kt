package com.example.ecomm.core.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ecomm.core.theme.AppColors

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        snackbarHost = snackbarHost,
        containerColor = if (isSystemInDarkTheme()) AppColors.darkBackground else AppColors.scaffoldBackground,
        contentColor = if (isSystemInDarkTheme()) AppColors.textOnPrimary else AppColors.textPrimary,
        content = content
    )
}