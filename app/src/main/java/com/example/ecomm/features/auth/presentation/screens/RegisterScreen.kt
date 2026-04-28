package com.example.ecomm.features.auth.presentation.screens

import com.example.ecomm.features.auth.presentation.viewmodel.state.RegisterState
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

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onRedirect: ()-> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val cmPasswordFocusRequester = remember { FocusRequester() }


    val scrollState = rememberScrollState()

    // collect StateFlow as state
    val uiState by viewModel.registerUiState.collectAsStateWithLifecycle()
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    // Listen to one-time events
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                AuthEvent.RegisterSuccess -> {
                    AppDialogBox.success("Register successful")
                    onRegisterSuccess()
                }

                is AuthEvent.RegisterError -> {
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
            AuthHeader(
                title = "Create Account",
                subtitle = "Start shopping with your new account",
            )
            Spacer(modifier = Modifier.height(42.dp))
            AuthFormCard {
                /// ─── name ────────────────────────────────────────────
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
                    onDone = { passwordFocusRequester.requestFocus() },
                    onValueChange = {
                        viewModel.onFormFieldChange(
                            AuthFieldEnum.NAME,
                            it,
                        )
                    },
                )
                Spacer(modifier = Modifier.height(19.dp))

                /// ─── Email ────────────────────────────────────────────
                AppTextField(
                    modifier = Modifier.focusRequester(emailFocusRequester),
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
                    imeAction = ImeAction.Next,
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
                        )
                    },
                    onDone = { cmPasswordFocusRequester.requestFocus() }
                )
                Spacer(modifier = Modifier.height(19.dp))

                /// ─── Confirm Password ────────────────────────────────────────────
                AppTextField(
                    modifier = Modifier.focusRequester(cmPasswordFocusRequester),
                    value = uiState.confirmPassword,
                    hintText = "Confirm password",
                    keyboardType = KeyboardType.Password,
                    errorMessage = uiState.confirmPasswordError,
                    obscureText = true,
                    isError = uiState.confirmPasswordError != null,
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
                            AuthFieldEnum.CONFIRM_PASSWORD,
                            it,
                        )
                    },
                    onDone = { viewModel.onRegisterClick() }
                )


                Spacer(modifier = Modifier.height(28.dp))

                // Register button
                AppButton(
                    label = "Register",
                    isLoading = authState.registerState is RegisterState.Loading,
                    gradient = AppColors.primaryGradient,
                    onClick =
                        viewModel::onRegisterClick
                )
                AuthRedirectText(
                    question = "Already have an account?",
                    actionLabel = "Login",
                    onTap = onRedirect
                )
            }
        }

    }
}
