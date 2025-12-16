package com.delecrode.devhub.domain.repository

import com.delecrode.devhub.domain.model.UserAuth
import com.delecrode.devhub.utils.Result

interface AuthRepository {

    suspend fun signIn(email: String, password: String): Result<UserAuth>

    suspend fun signUp(
        name: String,
        username: String,
        email: String,
        password: String
    ): Result<Unit>
    suspend fun signOut()


}