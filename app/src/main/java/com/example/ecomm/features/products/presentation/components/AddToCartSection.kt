package com.example.ecomm.features.products.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecomm.core.components.AppButton
import com.example.ecomm.core.theme.AppColors

@Composable
fun AddToCartSection(
    isLoading: Boolean = false,
    onAddToCart: (Int)-> Unit
){
    var  quantity by remember { mutableStateOf(1) }

    fun onIncrease(){
        quantity++

    }
    fun onDecrease(){
        if(quantity == 1) return
        quantity--
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(color= MaterialTheme.colorScheme.surface).padding(top = 14.dp, bottom = 24.dp, start = 10.dp, end = 20.dp)
    ) {
        IconButton(
            onClick = {
                onDecrease()
            }
        ) {
            Icon(Icons.Default.RemoveCircle, contentDescription = "Decrease quantity", modifier = Modifier.size(30.dp))
        }
        Text("$quantity", style = MaterialTheme.typography.titleLarge)
        IconButton(
            onClick = {
                onIncrease()
            }
        ) {
            Icon(Icons.Default.AddCircle, contentDescription = "Increase quantity", modifier = Modifier.size(30.dp))
        }
        Spacer(
            modifier = Modifier.width(24.dp)
        )
        AppButton(
            label = "Add to Cart",
            isLoading = isLoading,
            gradient = AppColors.primaryGradient,
            onClick = { onAddToCart(quantity)}
        )
    }

}
