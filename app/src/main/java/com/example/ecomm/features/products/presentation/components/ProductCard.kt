package com.example.ecomm.features.products.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.ecomm.features.products.domain.entities.ProductEntity

@Composable
fun ProductCard(
    product: ProductEntity,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .shadow(
                elevation = 14.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = AppColors.shadow,
                spotColor = AppColors.shadow,
            )
            .background(if (isSystemInDarkTheme()) AppColors.darkSurface else AppColors.surface)

    ) {
        // Image Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color(0xFFF1EEEA)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder),
            )
        }

        // Text Content
        Column(
            modifier = Modifier.padding( 10.dp)
        ) {
            Text(
                text = product.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp, lineHeight = 16.sp )
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.W800,
                        color = AppColors.primaryDark
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
                RatingComponent(rating = product.rating)
            }

        }
    }
}