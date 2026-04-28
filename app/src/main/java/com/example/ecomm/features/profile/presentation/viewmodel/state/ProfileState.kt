package com.example.ecomm.features.profile.presentation.viewmodel.state

import com.example.ecomm.core.error.Failure
import com.example.ecomm.features.profile.domain.entities.UserEntity

data class ProfileState (
    val  fetchProfileState: FetchProfileState = FetchProfileState.Initial,
    val updateProfileState: UpdateProfileState = UpdateProfileState.Initial
)

sealed class  FetchProfileState{
    object Initial : FetchProfileState()
    object Loading: FetchProfileState()
    data class Success(val user: UserEntity): FetchProfileState()
    data class Error(val failure: Failure): FetchProfileState()
}

sealed class UpdateProfileState{
    object  Initial : UpdateProfileState()
    object Loading: UpdateProfileState()
    object Success: UpdateProfileState()
    data class Error(val failure: Failure): UpdateProfileState()
}
