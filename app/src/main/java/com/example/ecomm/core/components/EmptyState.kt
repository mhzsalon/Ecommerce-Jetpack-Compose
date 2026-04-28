package com.example.ecomm.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecomm.core.theme.AppColors

@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    description: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    val textStyle = MaterialTheme.typography

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Icon container
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        brush = AppColors.primaryGradient // Brush
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AppColors.textOnPrimary,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Title
            Text(
                text = title,
                style = textStyle.headlineMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Description
            Text(
                text = description,
                textAlign = TextAlign.Center,
                style = textStyle.bodyMedium.copy(
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Optional button
            if (actionLabel != null) {
                AppButton(
                    label = actionLabel,
                    onClick = onAction,
                    backgroundColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}