package com.delecrode.devhub.domain.repository

import com.delecrode.devhub.domain.model.RepoDetail

interface RepoRepository {

    suspend fun getRepoDetail(owner: String, repo: String): RepoDetail
}