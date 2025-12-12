package com.delecrode.devhub.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun signIn(email: String, password: String): FirebaseUser

    suspend fun signUp(
        name: String,
        username: String,
        email: String,
        password: String
    ): Boolean
    suspend fun signOut()


}