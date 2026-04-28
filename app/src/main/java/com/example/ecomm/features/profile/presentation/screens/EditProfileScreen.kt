package com.example.ecomm.features.profile.presentation.screens

import android.util.Log.v
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecomm.core.components.AppButton
import com.example.ecomm.core.components.AppScaffold
import com.example.ecomm.core.components.AppTextField
import com.example.ecomm.core.components.CustomAppBar
import com.example.ecomm.core.helper.DialogBox.AppDialogBox
import com.example.ecomm.features.auth.presentation.components.AuthFormCard
import com.example.ecomm.features.auth.presentation.viewmodel.enums.AuthFieldEnum
import com.example.ecomm.features.profile.presentation.viewmodel.ProfileEvent
import com.example.ecomm.features.profile.presentation.viewmodel.ProfileViewModel
import com.example.ecomm.features.profile.presentation.viewmodel.state.UpdateProfileState

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val emailFocusRequester = remember { FocusRequester() }

    val uiState by viewModel.updateProfileUiState.collectAsStateWithLifecycle()
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.onInitializeProfileValue()

        viewModel.events.collect { event ->
            when (event) {
                ProfileEvent.UpdateSuccess -> {
                    AppDialogBox.success(
                        "Profile updated successfully"
                    )
                }
                is ProfileEvent.UpdateError -> {
                    AppDialogBox.error(event.message)
                }
            }
        }
    }

    AppScaffold(
        topBar = {
            CustomAppBar(
                title = "Edit Profile",
                onBack = {
                    onBack()
                }
            )
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            AuthFormCard {
                Text(
                    "Update your account information",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W800,
                    ),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Make sure your name and email stay current so your profile stays in sync.",
                    style = MaterialTheme.typography.bodyMedium
                        .copy(lineHeight = 15.sp),
                )
                Spacer(modifier = Modifier.height(24.dp))
                /// Name field
                AppTextField(
                    value = uiState.name,
                    hintText = "Full name",
                    keyboardType = KeyboardType.Text,
                    isError = uiState.nameError != null,
                    errorMessage = uiState.nameError,
                    imeAction = ImeAction.Next,
                    leadingIcon = {
                        Icon(Icons.Outlined.Person, contentDescription = null)
                    },
                    onValueChange = {
                        viewModel.onFieldChange(
                            AuthFieldEnum.NAME,
                            it,
                        )
                    },
                    onDone = { emailFocusRequester.requestFocus() }
                )
                Spacer(modifier = Modifier.height(18.dp))

                /// Email field
                AppTextField(
                    modifier = Modifier.focusRequester(emailFocusRequester),
                    value = uiState.email,
                    hintText = "Email address",
                    keyboardType = KeyboardType.Email,
                    isError = uiState.emailError != null,
                    errorMessage = uiState.emailError,
                    leadingIcon = {
                        Icon(Icons.Outlined.Email, contentDescription = null)
                    },
                    onValueChange = {
                        viewModel.onFieldChange(
                            AuthFieldEnum.EMAIL,
                            it,
                        )
                    },
                    onDone = {
                        viewModel.onUpdateProfile()
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                AppButton(
                    label = "Save Changes",
                    isLoading = profileState.updateProfileState is UpdateProfileState.Loading,
                    onClick = { viewModel.onUpdateProfile() }
                )
            }
        }
    }
}
