package com.example.ecomm.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ecomm.core.theme.AppColors

enum class AppDialogType { LOADING, SUCCESS, ERROR }

@Composable
fun AppStatusDialog(
    type: AppDialogType,
    message: String,
    onDismiss: () -> Unit = {},
) {
    val isLoading = type == AppDialogType.LOADING

    Dialog(
        onDismissRequest = { if (!isLoading) onDismiss() }, // ← block tap outside
        properties = DialogProperties(
            dismissOnBackPress = !isLoading,       // ← block back button
            dismissOnClickOutside = !isLoading,    // ← block tap outside
        )
    ) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 28.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = AppColors.primary)
                } else {
                    val isSuccess = type == AppDialogType.SUCCESS
                    val iconColor = if (isSuccess) AppColors.success else AppColors.error
                    val bgColor = if (isSuccess) AppColors.success.copy(alpha = 0.15f)
                    else AppColors.error.copy(alpha = 0.15f)
                    val icon = if (isSuccess) Icons.Default.Check else Icons.Default.Close

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(bgColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W600
                    )
                )
            }
        }
    }
}