package com.delecrode.devhub.domain.repository

import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.User

interface GitRepository {
    suspend fun getUser(userName: String): User
    suspend fun getRepos(userName: String): List<Repos>

}

