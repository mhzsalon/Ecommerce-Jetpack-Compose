package com.example.ecomm.features.profile.presentation.viewmodel.state

data class UpdateProfileUiState (
        val email: String = "",
        val name: String = "",
        val emailError: String? = null,
        val nameError: String? = null,
)