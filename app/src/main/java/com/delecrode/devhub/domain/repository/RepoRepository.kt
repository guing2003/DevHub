package com.delecrode.devhub.domain.repository

import com.delecrode.devhub.domain.model.Languages
import com.delecrode.devhub.domain.model.RepoDetail
import com.delecrode.devhub.domain.model.RepoFav
import com.delecrode.devhub.utils.Result
import kotlinx.coroutines.flow.Flow

interface RepoRepository {

    suspend fun getRepoDetail(owner: String, repo: String): Result<RepoDetail>

    suspend fun getLanguagesRepo(owner: String, repo: String): Result<Languages>

    suspend fun save(repo: RepoFav)
    fun getAll(): Flow<List<RepoFav>>
    fun getUserName(): Flow<String?>

}