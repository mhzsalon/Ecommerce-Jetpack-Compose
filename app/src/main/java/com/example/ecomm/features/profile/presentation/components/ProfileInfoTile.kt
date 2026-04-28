package com.example.ecomm.features.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecomm.core.theme.AppColors

@Composable
fun ProfileInfoTile(
    title: String,
    leadingIcon: ImageVector? = null,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    titleColor: Color? = null,
    showTrailIcon: Boolean = true,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = containerColor,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(16.dp)
    ) {
        if (leadingIcon != null) {
            Icon(
                leadingIcon,
                tint = AppColors.primaryDark,
                modifier = Modifier.size(20.dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(14.dp))
        }
        Text(
            title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 15.sp,
                color = titleColor ?: MaterialTheme.colorScheme.onPrimary
            ),
            textAlign = if(leadingIcon == null) TextAlign.Center else null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        if (showTrailIcon)
            Icon(
                Icons.Default.ArrowForwardIos,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
    }
}