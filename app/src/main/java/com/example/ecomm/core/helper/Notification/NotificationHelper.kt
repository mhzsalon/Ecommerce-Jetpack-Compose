package com.example.ecomm.core.helper.Notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.ecomm.R

object NotificationHelper {
    private const val CHANNEL_ID   = "ecomm_channel"
    private const val CHANNEL_NAME = "Ecomm Notifications"

    fun createChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Ecomm app notifications"
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    // Returns true if permission is already granted, false if it needs to be requested
    fun hasPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true  // permission not required below Android 13
        }
    }

    // Call this composable function to request permission if not granted
    @Composable
    fun RequestPermission(
        onGranted: () -> Unit = {},
        onDenied: () -> Unit = {}
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) onGranted() else onDenied()
            }

            LaunchedEffect(Unit) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            // no permission needed below Android 13 — fire granted immediately
            LaunchedEffect(Unit) { onGranted() }
        }
    }
    fun show(
        context: Context,
        title: String,
        message: String,
        id: Int = System.currentTimeMillis().toInt()
    ) {
        if (!hasPermission(context)) return  // ← silences the lint warning

        try {
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context).notify(id, notification)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}