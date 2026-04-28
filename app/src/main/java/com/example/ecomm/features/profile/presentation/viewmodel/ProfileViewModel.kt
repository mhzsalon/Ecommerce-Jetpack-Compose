package com.example.ecomm.features.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomm.core.utils.FormValidator
import com.example.ecomm.features.auth.domain.usecases.GetCachedUserUsecase
import com.example.ecomm.features.auth.presentation.viewmodel.enums.AuthFieldEnum
import com.example.ecomm.features.profile.domain.usecases.FetchProfileUseCase
import com.example.ecomm.features.profile.domain.usecases.UpdateProfileUsecase
import com.example.ecomm.features.profile.presentation.viewmodel.state.FetchProfileState
import com.example.ecomm.features.profile.presentation.viewmodel.state.ProfileState
import com.example.ecomm.features.profile.presentation.viewmodel.state.UpdateProfileState
import com.example.ecomm.features.profile.presentation.viewmodel.state.UpdateProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileEvent {
    data object UpdateSuccess : ProfileEvent
    data class UpdateError(val message: String) : ProfileEvent
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUsecase,
    private val getCachedUserUseCase: GetCachedUserUsecase
) : ViewModel() {


    /// Update Profile UI State
    private val _updateProfileUiState = MutableStateFlow(UpdateProfileUiState())
    val updateProfileUiState = _updateProfileUiState.asStateFlow()

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    private val _events = MutableSharedFlow<ProfileEvent>()
    val events = _events.asSharedFlow()

    private var userId: String? = null


    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            refreshUserId(
                onError = { failure ->
                    _profileState.update {
                        it.copy(
                            fetchProfileState = FetchProfileState.Error(
                                failure
                            )
                        )
                    }
                }
            )
            onFetchProfile()
        }
    }

    private suspend fun refreshUserId(
        onError: ((com.example.ecomm.core.error.Failure) -> Unit)? = null
    ): String? {
        return getCachedUserUseCase(Unit).fold(
            ifLeft = { failure ->
                onError?.invoke(failure)
                userId = null
                null
            },
            ifRight = { id ->
                userId = id
                id
            }
        )
    }

    fun onFetchProfile() {
        viewModelScope.launch {
            val id = refreshUserId(
                onError = { failure ->
                    _profileState.update {
                        it.copy(fetchProfileState = FetchProfileState.Error(failure))
                    }
                }
            )
            if (id.isNullOrBlank()) {
                _profileState.update { it.copy(fetchProfileState = FetchProfileState.Initial) }
                return@launch
            }

            _profileState.update { it.copy(fetchProfileState = FetchProfileState.Loading) }

            fetchProfileUseCase(id).onRight { user ->
                _profileState.update {
                    it.copy(fetchProfileState = FetchProfileState.Success(user))
                }
            }.onLeft { failure ->
                _profileState.update {
                    it.copy(fetchProfileState = FetchProfileState.Error(failure))
                }
            }
        }
    }

    fun onFieldChange(field: AuthFieldEnum, value: String) {
        _updateProfileUiState.update {
            when (field) {
                AuthFieldEnum.NAME -> it.copy(name = value, nameError = null)
                AuthFieldEnum.EMAIL -> it.copy(email = value, emailError = null)
                else -> it
            }
        }
    }

    fun onValidateUpdateProfile(): Boolean {
        _updateProfileUiState.update {
            it.copy(
                emailError = FormValidator.validateEmail(it.email),
                nameError = FormValidator.validateName(it.name)

            )
        }
        return with(_updateProfileUiState.value) {
            emailError == null && nameError == null
        }
    }

    fun onInitializeProfileValue() {
        val currentUser =
            (profileState.value.fetchProfileState as? FetchProfileState.Success)?.user ?: return
        _updateProfileUiState.update {
            it.copy(
                name = currentUser.fullName,
                nameError = null,
                email = currentUser.email,
                emailError = null
            )
        }
    }


    fun onUpdateProfile() {
        if (!onValidateUpdateProfile()) return

        // get current user from fetch state — return if not in success state
        val currentUser =
            (profileState.value.fetchProfileState as? FetchProfileState.Success)?.user ?: return

        viewModelScope.launch {
            _profileState.update { it.copy(updateProfileState = UpdateProfileState.Loading) }

            // create updated user with new values
            val updatedUser = currentUser.copyWith(
                fullName = _updateProfileUiState.value.name,
                email = _updateProfileUiState.value.email,
            )
            updateProfileUseCase(updatedUser).onRight {
                // update fetch state with new user data so UI reflects changes immediately
                _profileState.update {
                    it.copy(
                        updateProfileState = UpdateProfileState.Initial,
                        fetchProfileState = FetchProfileState.Success(updatedUser)
                    )
                }
                _events.emit(ProfileEvent.UpdateSuccess)
            }.onLeft { failure ->
                _profileState.update {
                    it.copy(updateProfileState = UpdateProfileState.Initial)
                }
                _events.emit(ProfileEvent.UpdateError(failure.message))
            }
        }
    }
}
