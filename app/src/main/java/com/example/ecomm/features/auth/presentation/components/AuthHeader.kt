package com.example.ecomm.features.auth.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun AuthHeader(title: String = "Welcome Back", subtitle: String = "Sign in to continue shopping") {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(com.example.ecomm.R.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier.height(64.dp)
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(title, style = MaterialTheme.typography.headlineLarge.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.W800
        ))
        Spacer(modifier = Modifier.height(10.dp))

        Text(subtitle, style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.typography.bodyMedium.color
        ))
    }

}