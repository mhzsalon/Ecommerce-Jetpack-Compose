package com.example.ecomm.features.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ecomm.core.theme.AppColors
@Composable
fun AuthFormCard(
    content: @Composable ColumnScope.() -> Unit
) {
    val isDark = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .shadow(
                elevation = 22.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = if (isDark) Color.Black.copy(alpha = 0.12f) else AppColors.shadow,
                spotColor = if (isDark) Color.Black.copy(alpha = 0.12f) else AppColors.shadow,
            )
            .background(
                color = if (isDark) AppColors.darkSurface.copy(alpha = 0.72f) else MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                shape = RoundedCornerShape(28.dp)
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(28.dp),
                color = if (isDark) AppColors.primary.copy(alpha = 0.08f) else AppColors.border
            )
            .padding(24.dp),
        content = content
    )
}