package com.delecrode.devhub.domain.repository

import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.User

interface UserRepository {
    suspend fun getUser(userName: String): User
    suspend fun getRepos(userName: String): List<Repos>

}

