package com.example.ecomm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.ecomm.core.helper.DialogBox.AppDialogObserver
import com.example.ecomm.core.helper.Notification.NotificationHelper
import com.example.ecomm.core.navigation.AppNavHost
import com.example.ecomm.core.theme.EcommTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createChannel(this)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            EcommTheme {
                AppDialogObserver(
                    navController
                )
                AppNavHost(
                    navController
                )
            }
        }
    }
}
