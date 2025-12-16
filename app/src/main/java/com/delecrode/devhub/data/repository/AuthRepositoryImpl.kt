package com.delecrode.devhub.data.repository

import com.delecrode.devhub.data.local.dataStore.AuthLocalDataSource
import com.delecrode.devhub.data.mapper.toUserAuthDomain
import com.delecrode.devhub.data.remote.firebase.FirebaseAuth
import com.delecrode.devhub.data.remote.firebase.UserExtraData
import com.delecrode.devhub.domain.model.RegisterUser
import com.delecrode.devhub.domain.model.UserAuth
import com.delecrode.devhub.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.delecrode.devhub.utils.Result
import com.delecrode.devhub.utils.mapAuthError
import com.delecrode.devhub.utils.mapSignUpError

class AuthRepositoryImpl(
    private val authDataSource: FirebaseAuth,
    private val userExtraDataSource: UserExtraData,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<UserAuth> {
        return try {
            val firebaseUser = authDataSource.signIn(email, password)
                ?: return Result.Error("Erro ao autenticar usuário")
            authLocalDataSource.saveUID(firebaseUser.uid)

            Result.Success(firebaseUser.toUserAuthDomain())

        } catch (e: Exception) {
            Result.Error(mapAuthError(e))
        }
    }


    override suspend fun signUp(
        name: String,
        username: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val uid = authDataSource.signUp(email, password)
                ?: return Result.Error("Erro ao criar conta")

            val userData = RegisterUser(
                fullName = name,
                username = username,
                email = email
            )

            userExtraDataSource.saveUserData(uid, userData)

            Result.Success(Unit)

        } catch (e: Exception) {
            Result.Error(mapSignUpError(e))
        }
    }


    override suspend fun signOut() {
        authDataSource.signOut()
        authLocalDataSource.clearUID()
    }
}
