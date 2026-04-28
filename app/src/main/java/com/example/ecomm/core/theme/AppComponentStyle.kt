package com.example.ecomm.core.theme

// ui/theme/AppComponentStyles.kt — equivalent of Flutter's button/input/card themes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

object AppComponentStyles {

    // ElevatedButton equivalent
    @Composable
    fun primaryButtonColors() = ButtonDefaults.buttonColors(
        containerColor = AppColors.primary,
        contentColor = AppColors.textOnPrimary,
        disabledContainerColor = AppColors.iconMuted,
        disabledContentColor = AppColors.surface,
    )

    // OutlinedButton equivalent
    @Composable
    fun outlinedButtonColors() = ButtonDefaults.outlinedButtonColors(
        contentColor = AppColors.textPrimary,
    )

    // TextButton equivalent
    @Composable
    fun textButtonColors() = ButtonDefaults.textButtonColors(
        contentColor = AppColors.primaryDark,
    )

    // Card equivalent
    @Composable
    fun cardColors(darkTheme: Boolean = isSystemInDarkTheme()) = CardDefaults.cardColors(
        containerColor = if (darkTheme) AppColors.darkSurface else AppColors.cardBackground,
    )

    // TextField equivalent
    @Composable
    fun inputColors(darkTheme: Boolean = isSystemInDarkTheme()) = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = if (darkTheme) AppColors.darkInputFill else AppColors.inputBackground,
        focusedContainerColor = if (darkTheme) AppColors.darkInputFill else AppColors.inputBackground,
        focusedBorderColor = AppColors.primary,
        unfocusedBorderColor = AppColors.border,
        errorBorderColor = AppColors.danger,
        focusedLabelColor = AppColors.textSecondary,
        unfocusedLabelColor = AppColors.textSecondary,
        errorLabelColor = AppColors.danger,
    )
}