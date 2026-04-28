package com.example.ecomm.features.products.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecomm.core.components.EmptyState
import com.example.ecomm.core.components.ErrorState
import com.example.ecomm.core.components.LoadingState
import com.example.ecomm.features.products.domain.entities.ProductEntity
import com.example.ecomm.features.products.presentation.components.ProductCard
import com.example.ecomm.features.products.presentation.components.SearchBar
import com.example.ecomm.features.products.presentation.viewmodel.ProductViewModel
import com.example.ecomm.features.products.presentation.viewmodel.state.FetchProductState

@Composable
fun ProductScreen(
    onNavigateTo: (String) -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val state by viewModel.productState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onFetchProduct()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Discover",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W800)
        )
        Spacer(modifier = Modifier.height(10.dp))

        SearchBar(
            query = state.searchQuery,
            onQueryChange = viewModel::onSearchQueryChange,
        )

        when (val fetchState = state.fetchProductState) {
            is FetchProductState.Loading -> {
                LoadingState()
            }

            is FetchProductState.Success -> {
                if (fetchState.productList.isEmpty())
                    EmptyState(
                        icon = Icons.Default.Info,
                        title = "No Products",
                        description = "",
                    )
                else
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(fetchState.productList.count()) { index ->
                            ProductCard(
                                product = fetchState.productList[index], modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { onNavigateTo(fetchState.productList[index].id) })
                        }
                    }
            }

            is FetchProductState.Error -> {
                ErrorState(
                    title = "Error",
                    message = fetchState.failure.message,
                    onRetry = { viewModel.onFetchProduct() }
                )

            }

            else -> Unit
        }
    }

}