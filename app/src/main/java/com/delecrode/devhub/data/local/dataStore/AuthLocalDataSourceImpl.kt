package com.delecrode.devhub.data.local.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("auth_prefs")

class AuthLocalDataSourceImpl(private val context: Context) : AuthLocalDataSource {

    private object PreferencesKeys {
        val UID_KEY = stringPreferencesKey("uid")
    }

    override fun getUID(): Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[PreferencesKeys.UID_KEY]
        }


    override suspend fun saveUser(uid: String) {
        Log.i("AuthLocalDataSourceImpl", "saveUser: $uid")
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.UID_KEY] = uid
        }
    }
}