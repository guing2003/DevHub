package com.delecrode.devhub.domain.repository

import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit

interface UserRepository {
    suspend fun getUserForGitHub(userName: String): UserForGit
    suspend fun getRepos(userName: String): List<Repos>

    suspend fun getUserForFirebase(): UserForFirebase

}

