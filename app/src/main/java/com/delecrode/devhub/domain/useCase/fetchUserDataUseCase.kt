package com.delecrode.devhub.domain.useCase

import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.UserRepository

class FetchUserDataUseCase(
    private val userRepository: UserRepository,
) {
    suspend fun loadUserFromFirebase() =
        userRepository.getUserForFirebase()

    suspend fun loadUserFromGit(username: String) =
        userRepository.getUserForGitHub(username)

    suspend fun loadRepos(username: String) =
        userRepository.getRepos(username)
}
