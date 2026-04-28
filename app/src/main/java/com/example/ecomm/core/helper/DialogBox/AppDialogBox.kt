package com.example.ecomm.core.helper.DialogBox

import com.example.ecomm.core.components.AppDialogType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AppDialogBox {
    private val _state = MutableStateFlow<DialogState>(DialogState.Idle)
    val state = _state.asStateFlow()

    fun loading(message: String = "Please wait...") {
        _state.value = DialogState.Visible(AppDialogType.LOADING, message)
    }

    fun success(message: String) {
        _state.value = DialogState.Visible(AppDialogType.SUCCESS, message)
    }

    fun error(message: String) {
        _state.value = DialogState.Visible(AppDialogType.ERROR, message)
    }


    fun showLoginRequired() {
        _state.value = DialogState.LoginRequired
    }
    fun dismiss() {
        _state.value = DialogState.Idle
    }
}


sealed class DialogState {
    object Idle : DialogState()
    object LoginRequired : DialogState()
    data class Visible(val type: AppDialogType, val message: String) : DialogState()
}