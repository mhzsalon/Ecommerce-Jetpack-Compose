package com.example.ecomm.features.products.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecomm.core.theme.AppColors

@Composable
fun CategoryChip(
    category: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(AppColors.primary.copy(alpha = 0.12f))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = AppColors.primaryDark,
                fontWeight = FontWeight.W700
            )
        )
    }
}
