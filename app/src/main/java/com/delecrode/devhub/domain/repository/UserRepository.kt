package com.delecrode.devhub.domain.repository

import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit
import com.delecrode.devhub.utils.Result

interface UserRepository {
    suspend fun getUserForGitHub(userName: String): Result<UserForGit>
    suspend fun getRepos(userName: String): Result<List<Repos>>

    suspend fun getUserForFirebase(): Result<UserForFirebase>

}

