package com.example.ecomm.features.auth.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecomm.core.components.AppButton
import com.example.ecomm.core.components.AppScaffold
import com.example.ecomm.core.components.AppTextField
import com.example.ecomm.core.helper.DialogBox.AppDialogBox
import com.example.ecomm.core.theme.AppColors
import com.example.ecomm.features.auth.presentation.components.AuthFormCard
import com.example.ecomm.features.auth.presentation.components.AuthHeader
import com.example.ecomm.features.auth.presentation.components.AuthRedirectText
import com.example.ecomm.features.auth.presentation.viewmodel.AuthEvent
import com.example.ecomm.features.auth.presentation.viewmodel.AuthViewModel
import com.example.ecomm.features.auth.presentation.viewmodel.enums.AuthFieldEnum
import com.example.ecomm.features.auth.presentation.viewmodel.state.LoginState

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRedirect: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val passwordFocusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()
    // collect StateFlow as state
    val uiState by viewModel.loginUiState.collectAsStateWithLifecycle()
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    // Listen to one-time events
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                AuthEvent.LoginSuccess -> {
                    AppDialogBox.success("Login successful")
                    onLoginSuccess()
                }

                is AuthEvent.LoginError -> {
                    AppDialogBox.error(event.message)
                }

                else -> Unit
            }
        }
    }
    AppScaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .imePadding()
                .padding(horizontal = 24.dp)
        ) {
            AuthHeader()
            Spacer(modifier = Modifier.height(42.dp))
            AuthFormCard {
                /// ─── Email ────────────────────────────────────────────
                AppTextField(
                    value = uiState.email,
                    hintText = "Email address",
                    keyboardType = KeyboardType.Email,
                    isError = uiState.emailError != null,
                    errorMessage = uiState.emailError,
                    imeAction = ImeAction.Next,
                    leadingIcon = {
                        Icon(Icons.Outlined.Email, contentDescription = null)
                    },
                    onDone = { passwordFocusRequester.requestFocus() },
                    onValueChange = {
                        viewModel.onFormFieldChange(
                            AuthFieldEnum.EMAIL,
                            it,
                            isRegister = false
                        )
                    },

                    )
                Spacer(modifier = Modifier.height(19.dp))
                /// ─── Password ────────────────────────────────────────────
                AppTextField(
                    modifier = Modifier.focusRequester(passwordFocusRequester),
                    value = uiState.password,
                    hintText = "Password",
                    keyboardType = KeyboardType.Password,
                    errorMessage = uiState.passwordError,
                    obscureText = true,
                    isError = uiState.passwordError != null,
                    leadingIcon = {
                        Icon(Icons.Outlined.Lock, contentDescription = null)
                    },
                    trailingIcon = { isVisible, toggle ->
                        IconButton(onClick = toggle) {
                            Icon(
                                imageVector = if (isVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if (isVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    onValueChange = {
                        viewModel.onFormFieldChange(
                            AuthFieldEnum.PASSWORD,
                            it,
                            isRegister = false
                        )
                    },
                    onDone = { viewModel.onLoginClick() }
                )


                Spacer(modifier = Modifier.height(28.dp))

                // login button
                AppButton(
                    label = "Login",
                    isLoading = authState.loginState is LoginState.Loading,
                    gradient = AppColors.primaryGradient,
                    onClick =
                        viewModel::onLoginClick
                )
                AuthRedirectText(
                    question = "Don't have an account?",
                    actionLabel = "Register",
                    onTap = onRedirect
                )
            }
        }

    }
}
