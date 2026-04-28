package com.example.ecomm.features.auth.presentation.components

import android.R.attr.fontWeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.ecomm.core.theme.AppColors

@Composable
fun AuthRedirectText(
    question: String,
    actionLabel: String,
    onTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = question,
            style = MaterialTheme.typography.bodyMedium,
        )
        TextButton(onClick = onTap) {
            Text(
                text = actionLabel,
                color = AppColors.primary,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W700
                )
            )
        }
    }
}