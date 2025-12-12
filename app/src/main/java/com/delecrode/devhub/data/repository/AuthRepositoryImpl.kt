package com.delecrode.devhub.data.repository

import com.delecrode.devhub.data.firebase.FirebaseAuth
import com.delecrode.devhub.data.firebase.UserExtraData
import com.delecrode.devhub.domain.model.RegisterUser
import com.delecrode.devhub.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImpl(
    private val authDataSource: FirebaseAuth,
    private val userExtraDataSource: UserExtraData
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): FirebaseUser {
        try {
            val response = authDataSource.signIn(email, password)
            return response ?: throw Exception("Erro ao recuperar usuário após login")
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseAuthInvalidUserException -> "Usuário não encontrado."
                is FirebaseAuthInvalidCredentialsException -> "Senha incorreta ou e-mail inválido."
                is FirebaseAuthUserCollisionException -> "Este e-mail já está em uso."
                is FirebaseAuthWeakPasswordException -> "A senha deve ter pelo menos 6 caracteres."
                else -> e.message ?: "Erro desconhecido ao fazer login"
            }
            throw Exception(errorMessage)
        }
    }

    override suspend fun signUp(
        name: String,
        username: String,
        email: String,
        password: String
    ): Boolean {
        try {
            val uid = authDataSource.signUp(email, password) ?: return false

            val userData = RegisterUser(
                fullName = name,
                username = username,
                email = email
            )

            userExtraDataSource.saveUserData(uid, userData)

            return true
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseAuthUserCollisionException -> "Este e-mail já está em uso."
                is FirebaseAuthWeakPasswordException -> "A senha deve ter pelo menos 6 caracteres."
                is FirebaseAuthInvalidCredentialsException -> "E-mail inválido."
                else -> e.message ?: "Erro desconhecido ao cadastrar"
            }
            throw Exception(errorMessage)
        }
    }

    override suspend fun signOut() {
        authDataSource.signOut()
    }
}
