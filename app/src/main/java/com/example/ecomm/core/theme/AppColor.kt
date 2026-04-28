package com.example.ecomm.core.theme

// ui/theme/AppColors.kt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

object AppColors {

    // Primary brand color
    val primary = Color(0xFFFF8C1A)
    val primaryDark = Color(0xFFE07A16)
    val primaryLight = Color(0xFFFFC247)

    // Supporting accent tones
    val accentAmber = Color(0xFFF4C430)
    val accentGold = Color(0xFFFFD65C)
    val warning = Color(0xFFFFB547)
    val danger = Color(0xFFFF2D2D)
    val error = Color(0xFFD32F2F)
    val success = Color(0xFF00C9A7)

    // Light shopping experience palette
    val scaffoldBackground = Color(0xFFFFF8EA)
    val secondaryBackground = Color(0xFFF7F4EE)
    val surface = Color(0xFFFFFFFF)
    val cardBackground = Color(0xFFFFFCF6)
    val inputBackground = Color(0xFFF4F1EA)
    val border = Color(0xFFE8E1D6)

    // Dark login experience palette
    val darkBackground = Color(0xFF1F1B18)
    val darkSurface = Color(0xFF2A241F)
    val darkInputFill = Color(0xFF2B2520)

    // Typography and icon colors
    val textPrimary = Color(0xFF181411)
    val textSecondary = Color(0xFF8C7F73)
    val textOnPrimary = Color(0xFFFFFFFF)
    val textMuted = Color(0xFFA39588)
    val iconPrimary = Color(0xFF2E2925)
    val iconMuted = Color(0xFFB6ADA3)


    val white = Color(0xFFFFFFFF)

    // Utility colors
    val shadow = Color(0x14000000)
    val transparent = Color.Transparent

    // Gradients
    val primaryGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFFB11A), Color(0xFFFF7F21)),
    )

    val avatarGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFD54A), Color(0xFFFF9326))
    )
}