package com.example.ecomm.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecomm.core.theme.AppColors

@Composable
fun ErrorState(
    title: String,
    message: String,
    onRetry: (() -> Unit)? = null
) {
    val typography = MaterialTheme.typography

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Icon
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                tint = AppColors.danger,
                modifier = Modifier.size(42.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = title,
                style = typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Message
            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = typography.bodyMedium.copy(
                    lineHeight = 22.sp
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Retry button (optional)
            if (onRetry != null) {
                FilledTonalButton(
                    onClick = onRetry,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = AppColors.primary,
                        contentColor = AppColors.textOnPrimary
                    )
                ) {
                    Text("Try Again")
                }
            }
        }
    }
}