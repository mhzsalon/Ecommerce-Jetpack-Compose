package com.example.ecomm.features.profile.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecomm.core.components.ErrorState
import com.example.ecomm.core.components.LoadingState
import com.example.ecomm.core.extensions.toTitleCase
import com.example.ecomm.core.helper.DialogBox.AppDialogBox
import com.example.ecomm.core.theme.AppColors
import com.example.ecomm.features.auth.presentation.viewmodel.AuthEvent
import com.example.ecomm.features.auth.presentation.viewmodel.AuthViewModel
import com.example.ecomm.features.profile.domain.entities.UserEntity
import com.example.ecomm.features.profile.presentation.components.ProfileInfoTile
import com.example.ecomm.features.profile.presentation.viewmodel.ProfileViewModel
import com.example.ecomm.features.profile.presentation.viewmodel.state.FetchProfileState


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogoutSuccess: () -> Unit,
    authViewModel: AuthViewModel,
    onOpenEditProfile: ()-> Unit
) {
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onFetchProfile()
        authViewModel.events.collect { event ->
            when (event) {
                AuthEvent.LogoutSuccess -> {
                    AppDialogBox.success("Logout Successful")
                    onLogoutSuccess()
                }

                is AuthEvent.LogoutError -> {
                    AppDialogBox.error(event.message)
                }

                else -> Unit
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            "Profile",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W800)
        )

        when (val fetchState = profileState.fetchProfileState) {
            is FetchProfileState.Loading -> LoadingState()
            is FetchProfileState.Success -> ProfileBody(
                user = fetchState.user,
                onLogout = {
                    authViewModel.onLogoutUser()
                },
                onOpenEditProfile = {onOpenEditProfile()}
            )

            is FetchProfileState.Error -> ErrorState(
                title = "Error",
                message = fetchState.failure.message,
                onRetry = { viewModel.onFetchProfile() }
            )

            else -> {}
        }
    }
}


@Composable
private fun ProfileBody(
    user: UserEntity,
    onLogout: () -> Unit,
    onOpenEditProfile: ()-> Unit
) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Profile Avatar
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(108.dp)
                .background(
                    brush = AppColors.avatarGradient,
                    shape = CircleShape
                )
        ) {
            Text(
                user.fullName.take(1).uppercase(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 44.sp,
                    fontWeight = FontWeight.W500
                )
            )
        }

        Spacer(modifier = Modifier.height(18.dp))
        Text(
            user.fullName.toTitleCase(),
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 28.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            user.email,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
        )
        Spacer(modifier = Modifier.height(35.dp))

        ProfileInfoTile(
            title = "Edit Profile",
            leadingIcon = Icons.Outlined.Edit,
            onClick = {onOpenEditProfile()}
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileInfoTile(
            title = "Logout",
            showTrailIcon = false,
            containerColor = AppColors.danger,
            titleColor = AppColors.white,
            onClick = { onLogout() }
        )
    }
}
