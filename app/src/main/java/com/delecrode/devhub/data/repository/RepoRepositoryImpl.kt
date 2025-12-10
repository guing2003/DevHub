package com.delecrode.devhub.data.repository

import com.delecrode.devhub.data.mapper.toRepoDetailDomain
import com.delecrode.devhub.data.remote.service.RepoApiService
import com.delecrode.devhub.domain.model.RepoDetail
import com.delecrode.devhub.domain.repository.RepoRepository

class RepoRepositoryImpl(val repoApi: RepoApiService) : RepoRepository {

    override suspend fun getRepoDetail(owner: String, repo: String): RepoDetail {
        try {
            val response = repoApi.getRepoDetail(owner, repo)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return body.toRepoDetailDomain()
                } else {
                    throw Exception("Resposta vazia do servidor")
                }
            } else {
                throw Exception("Erro na requisição ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}