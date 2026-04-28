package com.example.ecomm.core.localDB

// DataStoreProvider.kt
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// Must be at top-level (outside any class)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")