package com.example.ecomm.features.auth.presentation.viewmodel.state

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
)