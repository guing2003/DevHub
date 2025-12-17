package com.delecrode.devhub.domain.repository

import com.delecrode.devhub.domain.model.UserAuth
import com.delecrode.devhub.data.utils.Result

interface AuthRepository {

    suspend fun signIn(email: String, password: String): Result<UserAuth>

    suspend fun signUp(
        name: String,
        username: String,
        email: String,
        password: String
    ): Result<Unit>
    suspend fun signOut()
    suspend fun forgotPassword(email: String): Result<Unit>
}