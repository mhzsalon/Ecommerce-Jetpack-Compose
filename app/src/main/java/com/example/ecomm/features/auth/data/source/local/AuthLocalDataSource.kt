package com.example.ecomm.features.auth.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences
import com.example.ecomm.core.error.CacheException
import kotlinx.coroutines.flow.first

interface AuthLocalDataSource {
    suspend fun cacheUserId(userId: String)
    suspend fun getCachedUserId(): String?
    suspend fun deleteCachedUserId()
}

class AuthLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : AuthLocalDataSource {
    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    override suspend fun cacheUserId(userId: String) {
        try {
            dataStore.edit { prefs ->
                prefs[USER_ID_KEY] = userId
            }
        } catch (e: Exception){
            throw CacheException(e.message ?:  "Cached Error")
        }

    }

    override suspend fun getCachedUserId(): String? {
        return dataStore.data.first()[USER_ID_KEY]
    }

    //Deletes the user ID key from DataStore.
    override suspend fun deleteCachedUserId() {
        dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
    }
}