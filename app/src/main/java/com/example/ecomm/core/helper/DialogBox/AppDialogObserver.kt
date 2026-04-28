package com.example.ecomm.core.helper.DialogBox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ecomm.core.components.AppStatusDialog
import com.example.ecomm.core.components.LoginRequiredDialog
import com.example.ecomm.core.navigation.Routes

@Composable
fun AppDialogObserver(
    navController: NavController
) {
    val state by AppDialogBox.state.collectAsStateWithLifecycle()


    when (state) {
        is DialogState.Visible -> {
            val s = state as DialogState.Visible
            AppStatusDialog(
                type = s.type,
                message = s.message,
                onDismiss = { AppDialogBox.dismiss() }
            )
        }
        is DialogState.LoginRequired -> {
            LoginRequiredDialog(
                onLogin = {
                    AppDialogBox.dismiss()
                    navController.navigate(Routes.LOGIN)
                },
                onDismiss = { AppDialogBox.dismiss() }
            )
        }
        else -> Unit
    }

}
