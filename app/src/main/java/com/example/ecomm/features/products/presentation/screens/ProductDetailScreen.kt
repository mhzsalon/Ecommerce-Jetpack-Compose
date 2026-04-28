package com.example.ecomm.features.products.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ecomm.R
import com.example.ecomm.core.components.AppScaffold
import com.example.ecomm.core.components.CustomAppBar
import com.example.ecomm.core.extensions.toTitleCase
import com.example.ecomm.core.helper.DialogBox.AppDialogBox
import com.example.ecomm.core.theme.AppColors
import com.example.ecomm.features.auth.presentation.viewmodel.AuthViewModel
import com.example.ecomm.features.cart.presentation.viewmodel.CartEvent
import com.example.ecomm.features.cart.presentation.viewmodel.CartViewModel
import com.example.ecomm.features.cart.presentation.viewmodel.state.AddToCartState
import com.example.ecomm.features.products.presentation.components.AddToCartSection
import com.example.ecomm.features.products.presentation.components.CategoryChip
import com.example.ecomm.features.products.presentation.components.RatingComponent
import com.example.ecomm.features.products.presentation.viewmodel.ProductViewModel


@Composable
fun ProductDetailScreen(
    onBack: () -> Unit,
    id: String,
    authViewModel: AuthViewModel,
    onLoginRequired: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val productState by viewModel.productState.collectAsStateWithLifecycle()
    val cartState by cartViewModel.cartState.collectAsStateWithLifecycle()

    // fallback → fetch  product
    LaunchedEffect(productState.allProducts) {
        if (productState.allProducts.isEmpty()) {
            viewModel.onFetchProduct()
        }
    }
    // filter out product by id
    val product = productState.allProducts.find { it.id == id }


    LaunchedEffect(Unit) {
        cartViewModel.events.collect { event ->
            when (event) {
                CartEvent.AddToCartSuccess -> {
                    AppDialogBox.success("Added to cart successfully")
                }

                is CartEvent.AddToCartError -> {
                    AppDialogBox.error(event.message)
                }

                else -> {}
            }
        }

    }

    AppScaffold(
        topBar = {
            CustomAppBar(
                onBack = { onBack() }
            )
        },
        bottomBar = {
            AddToCartSection(
                isLoading = cartState.addToCartState is AddToCartState.Loading,
                onAddToCart = { quantity ->
                    if (authState.cachedUserId.isNullOrBlank()) {
                        onLoginRequired()
                        return@AddToCartSection
                    }

                    val currentProduct = product ?: return@AddToCartSection
                    cartViewModel.addToCart(
                        productEntity = currentProduct,
                        quantity = quantity
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            // Product Image builder----------------------------
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(32.dp),
                        ambientColor = AppColors.shadow,
                        spotColor = AppColors.shadow,
                    )
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product?.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder),
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            CategoryChip(category = product?.category?.toTitleCase() ?: "")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                product?.title ?: "",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 30.sp,
                )
            )
            Spacer(modifier = Modifier.height(14.dp))
            // Price and rating-------------------------------
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "$${product?.price ?: 0.0}",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.W800,
                        fontSize = 26.sp,
                        color = AppColors.primaryDark
                    )
                )
                Spacer(modifier = Modifier.width(14.dp))
                RatingComponent(rating = product?.rating ?: 0.0, size = 22.dp)
            }
            Spacer(modifier = Modifier.height(28.dp))

            // Product description-------------------------------
            Text(
                "Product Details",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 21.sp,
                    fontWeight = FontWeight.W800
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                product?.description ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 15.sp,
                    lineHeight = 16.5.sp,
                    color = AppColors.textSecondary
                )
            )
        }
    }
}




