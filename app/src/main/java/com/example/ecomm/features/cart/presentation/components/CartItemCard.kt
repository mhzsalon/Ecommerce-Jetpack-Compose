package com.example.ecomm.features.cart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ecomm.R
import com.example.ecomm.core.theme.AppColors
import com.example.ecomm.features.cart.domain.entities.CartEntity
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartItemCard(
    cartItem: CartEntity,
    onDelete: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    val totalAmt = NumberFormat.getCurrencyInstance(Locale.US).format(cartItem.totalPrice)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = AppColors.shadow,
                spotColor = AppColors.shadow
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
                color = if (isSystemInDarkTheme()) AppColors.primary.copy(alpha = 0.08f)
                else AppColors.border
            )
            .padding(14.dp, end = 14.dp, top = 14.dp, bottom = 10.dp),
    ) {
        // Product Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cartItem.product.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(84.dp)
                .width(84.dp)
                .background(
                    Color(0xFFF1EEEA), shape = RoundedCornerShape(12.dp)
                ),
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder),
        )

        Spacer(modifier = Modifier.width(14.dp))

        // Product Info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                cartItem.product.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W700
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "${cartItem.quantity} item",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Price + Quantity Controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    totalAmt,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)  // ← pushes controls to end
                )
                IconButton(onClick = onDecrement) {
                    Icon(
                        Icons.Default.RemoveCircle,
                        contentDescription = "Decrease",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(
                    "${cartItem.quantity}",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onIncrement) {
                    Icon(
                        Icons.Default.AddCircle,
                        contentDescription = "Increase",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        // Delete Button
        IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Outlined.Delete, contentDescription = null, tint = AppColors.primary,)
        }
    }
}