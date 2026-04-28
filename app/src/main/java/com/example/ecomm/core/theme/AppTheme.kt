package com.example.ecomm.core.theme

import androidx.compose.material3.*

// ─── Color Schemes ────────────────────────────────────────
 val LightColorScheme = lightColorScheme(
    primary = AppColors.primary,
    onPrimary = AppColors.textOnPrimary,
    secondary = AppColors.accentAmber,
    onSecondary = AppColors.textPrimary,
    error = AppColors.danger,
    onError = AppColors.textOnPrimary,
    surface = AppColors.surface,
    onSurface = AppColors.textPrimary,
    background = AppColors.scaffoldBackground,
    onBackground = AppColors.textPrimary,
    outline = AppColors.border,
   surfaceContainer = AppColors.surface,

)

 val DarkColorScheme = darkColorScheme(
    primary = AppColors.primary,
    onPrimary = AppColors.textOnPrimary,
    secondary = AppColors.primaryLight,
    onSecondary = AppColors.textPrimary,
    error = AppColors.danger,
    onError = AppColors.textOnPrimary,
    surface = AppColors.darkSurface,
    onSurface = AppColors.textOnPrimary,
    background = AppColors.darkBackground,
    onBackground = AppColors.textOnPrimary,
    outline = AppColors.darkInputFill,
    surfaceContainer = AppColors.darkSurface,
)
