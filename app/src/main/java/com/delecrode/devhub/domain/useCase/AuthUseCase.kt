package com.delecrode.devhub.domain.useCase

import com.delecrode.devhub.domain.repository.AuthRepository

class AuthUseCase (
    private val authRepository: AuthRepository

){
    suspend fun signOut() =
        authRepository.signOut()
}