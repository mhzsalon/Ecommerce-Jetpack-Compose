package com.example.ecomm.features.auth.presentation.viewmodel.state

import com.example.ecomm.core.error.Failure

data class AuthState(
    val cachedUserId: String? = null,
    val loginState: LoginState = LoginState.Initial,
    val registerState: RegisterState = RegisterState.Initial,
    val logoutState: LogoutState = LogoutState.Initial
)

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val failure: Failure) : LoginState()
}

sealed class RegisterState {
    object Initial : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val failure: Failure) : RegisterState()
}


sealed class LogoutState {
    object Initial : LogoutState()
    object Loading : LogoutState()
    object Success : LogoutState()
    data class Error(val failure: Failure) : LogoutState()
}
