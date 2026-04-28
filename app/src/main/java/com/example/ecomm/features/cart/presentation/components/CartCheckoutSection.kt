package com.example.ecomm.features.cart.presentation.components

import android.R.attr.label
import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecomm.core.components.AppButton
import com.example.ecomm.core.theme.AppColors
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartCheckoutSection(
    itemCount: Int,
    total: Double,
    isLoading: Boolean = false,
    onCheckout: () -> Unit
) {
    val formatted = NumberFormat.getCurrencyInstance(Locale.US).format(total)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = if (isSystemInDarkTheme())
                    AppColors.primary.copy(alpha = 0.08f)
                else AppColors.border
            )
            .padding(20.dp)
    ) {
        // total checkout price
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Total", style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp
                )
            )
            Text(
                "$formatted",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.W800,
                    fontSize = 24.sp,
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        //Cart item count
        Text(
            "$itemCount item${if (itemCount == 1) "" else "s"} in your bag",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 13.sp
            )
        )
        Spacer(modifier = Modifier.height(18.dp))

        AppButton(
            label = "Checkout",
            isLoading = isLoading,
            gradient = AppColors.primaryGradient,
            onClick = { onCheckout() }
        )
    }
}
