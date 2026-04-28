package com.example.ecomm


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// EcommApp.kt
@HiltAndroidApp                           // ← this is where Hilt initializes
class EcommApp : Application(){
    override fun onCreate() {
        super.onCreate()
        com.google.firebase.FirebaseApp.initializeApp(this)
    }
}