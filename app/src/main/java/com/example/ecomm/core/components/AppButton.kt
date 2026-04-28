package com.example.ecomm.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecomm.core.theme.AppColors

@Composable
fun AppButton(
    label: String,
    onClick: (() -> Unit)? = null,
    isLoading: Boolean = false,
    height: Dp = 55.dp,
    borderRadius: Dp = 18.dp,
    gradient: Brush? = null,
    backgroundColor: Color = AppColors.primary,
    contentColor: Color = Color.White,
) {

    val isEnabled = onClick != null && !isLoading

    val shape = RoundedCornerShape(borderRadius)

    Button(
        onClick = { onClick?.invoke() },
        enabled = isEnabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.7f),
            disabledContentColor = contentColor.copy(alpha = 0.7f)
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .background(brush = gradient ?: SolidColor(Color.Transparent), shape = shape)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = contentColor,
                strokeWidth = 2.4.dp,
                modifier = Modifier.size(22.dp)
            )
        } else {
            Text(
                text = label,
                color = contentColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }


    }
}