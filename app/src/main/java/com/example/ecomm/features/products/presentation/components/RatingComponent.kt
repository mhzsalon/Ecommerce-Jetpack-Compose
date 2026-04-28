package com.example.ecomm.features.products.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ecomm.core.theme.AppColors

@Composable
fun RatingComponent(
    rating: Double,
    size: Dp = 14.dp
) {
    val displayRating = rating.coerceIn(0.0, 5.0)

    Row {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = if (index < displayRating) {
                    AppColors.warning
                } else {
                    AppColors.border
                },
                modifier = Modifier.size(size)
            )
        }
    }
}