package com.delecrode.devhub.data.local.dataStore

import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    fun getUID(): Flow<String?>
    fun getUserName(): Flow<String?>
    suspend fun saveUID(uid: String)

    suspend fun saveUserName(name: String)

    suspend fun clearUID()
}