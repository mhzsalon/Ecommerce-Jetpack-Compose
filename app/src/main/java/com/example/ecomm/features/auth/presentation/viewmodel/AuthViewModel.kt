package com.example.ecomm.features.auth.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomm.core.utils.FormValidator
import com.example.ecomm.features.auth.domain.usecases.GetCachedUserUsecase
import com.example.ecomm.features.auth.domain.usecases.LoginParams
import com.example.ecomm.features.auth.domain.usecases.LoginUsecase
import com.example.ecomm.features.auth.domain.usecases.LogoutUserUseCase
import com.example.ecomm.features.auth.domain.usecases.RegisterParams
import com.example.ecomm.features.auth.domain.usecases.RegisterUsecase
import com.example.ecomm.features.auth.presentation.viewmodel.enums.AuthFieldEnum
import com.example.ecomm.features.auth.presentation.viewmodel.state.AuthState
import com.example.ecomm.features.auth.presentation.viewmodel.state.LoginState
import com.example.ecomm.features.auth.presentation.viewmodel.state.LoginUiState
import com.example.ecomm.features.auth.presentation.viewmodel.state.LogoutState
import com.example.ecomm.features.auth.presentation.viewmodel.state.RegisterState
import com.example.ecomm.features.auth.presentation.viewmodel.state.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AuthEvent {
    data object LoginSuccess : AuthEvent
    data class LoginError(val message: String) : AuthEvent
    data object RegisterSuccess : AuthEvent
    data class RegisterError(val message: String) : AuthEvent
    data object LogoutSuccess : AuthEvent
    data class LogoutError(val message: String) : AuthEvent
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val registerUsecase: RegisterUsecase,
    private val getCachedUserUsecase: GetCachedUserUsecase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel() {

    //login form state
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()


    // function for field changes
    fun onFormFieldChange(field: AuthFieldEnum, value: String, isRegister: Boolean = true) {
        if (isRegister) {
            _registerUiState.update {
                when (field) {
                    AuthFieldEnum.NAME -> it.copy(name = value, nameError = null)
                    AuthFieldEnum.EMAIL -> it.copy(email = value, emailError = null)
                    AuthFieldEnum.PASSWORD -> it.copy(password = value, passwordError = null)
                    AuthFieldEnum.CONFIRM_PASSWORD -> it.copy(
                        confirmPassword = value, confirmPasswordError = null
                    )
                }
            }
        } else {
            _loginUiState.update {
                when (field) {
                    AuthFieldEnum.EMAIL -> it.copy(email = value, emailError = null)
                    AuthFieldEnum.PASSWORD -> it.copy(password = value, passwordError = null)
                    else -> it // name and confirmPassword don't exist in login
                }
            }
        }
    }

    //register form state
    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerUiState = _registerUiState.asStateFlow()

    // api state
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    private val _events = MutableSharedFlow<AuthEvent>()
    val events = _events.asSharedFlow()

    init {
        onGetCachedUser()
    }


    private fun validateLoginForm(): Boolean {
        _loginUiState.update {
            it.copy(
                emailError = FormValidator.validateEmail(it.email),
                passwordError = FormValidator.validatePassword(it.password)
            )
        }

        return with(_loginUiState.value) {
            emailError == null && passwordError == null
        }


    }

    private fun validateRegisterForm(): Boolean {
        _registerUiState.update {
            it.copy(
                nameError = FormValidator.validateName(it.name),
                emailError = FormValidator.validateEmail(it.email),
                passwordError = FormValidator.validatePassword(it.password),
                confirmPasswordError = FormValidator.validateConfirmPassword(
                    it.password, it.confirmPassword
                )
            )
        }

        return with(_registerUiState.value) {
            nameError == null && emailError == null && passwordError == null && confirmPasswordError == null
        }
    }

    fun onLoginClick() {
        // returns if the login input is invalid
        if (!validateLoginForm()) return

        viewModelScope.launch {
            _authState.update { it.copy(loginState = LoginState.Loading) }

            loginUsecase(
                LoginParams(
                    email = _loginUiState.value.email, password = _loginUiState.value.password
                ),
            ).onRight { userId ->
                _authState.update {
                    Log.d("TAG", "cachedUserId viewmodel: $userId")
                    it.copy(
                        cachedUserId = userId, loginState = LoginState.Initial
                    )
                }
                _events.emit(AuthEvent.LoginSuccess)
            }.onLeft { failure ->
                _authState.update {
                    it.copy(loginState = LoginState.Initial)
                }
                _events.emit(AuthEvent.LoginError(failure.message))
            }

        }
    }

    fun onRegisterClick() {
        if (!validateRegisterForm()) return

        viewModelScope.launch {
            _authState.update { it.copy(registerState = RegisterState.Loading) }

            val registerVal = _registerUiState.value
            registerUsecase(
                RegisterParams(
                    fullName = registerVal.name,
                    email = registerVal.email,
                    password = registerVal.password,
                )
            ).onRight { userId ->
                _authState.update {
                    it.copy(
                        cachedUserId = userId, registerState = RegisterState.Initial
                    )
                }
                _events.emit(AuthEvent.RegisterSuccess)
            }.onLeft { failure ->
                _authState.update {
                    it.copy(registerState = RegisterState.Initial)
                }
                _events.emit(AuthEvent.RegisterError(failure.message))
            }
        }
    }


    fun onGetCachedUser() {
        viewModelScope.launch {
            getCachedUserUsecase(Unit).onRight { userId ->
                _authState.update {
                    it.copy(
                        cachedUserId = userId
                    )
                }
            }
        }
    }

    fun onLogoutUser() {
        viewModelScope.launch {
            _authState.update { it.copy(logoutState = LogoutState.Loading) }
            logoutUserUseCase(Unit).onRight {
                _authState.update {
                    it.copy(
                        logoutState = LogoutState.Initial,
                        cachedUserId = null
                    )
                }
                _events.emit(AuthEvent.LogoutSuccess)

            }.onLeft { failure ->
                _authState.update { it.copy(logoutState = LogoutState.Initial) }
                _events.emit(AuthEvent.LogoutError(failure.message))

            }
        }
    }
}
