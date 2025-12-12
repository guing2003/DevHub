package com.delecrode.devhub.data.local.dataStore

import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    fun getUID(): Flow<String?>
    suspend fun saveUser(uid: String)
}