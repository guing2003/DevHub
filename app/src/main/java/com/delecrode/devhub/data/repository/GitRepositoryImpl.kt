package com.delecrode.devhub.data.repository

import android.util.Log
import com.delecrode.devhub.data.mapper.toRepoDetailDomain
import com.delecrode.devhub.data.mapper.toReposDomain
import com.delecrode.devhub.data.mapper.toUserDomain
import com.delecrode.devhub.data.remote.service.GitApiService
import com.delecrode.devhub.domain.model.RepoDetail
import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.User
import com.delecrode.devhub.domain.repository.GitRepository

class GitRepositoryImpl(private val apiService: GitApiService) : GitRepository {

    override suspend fun getUser(userName: String): User {
        try {
            val response = apiService.getUser(userName)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.i("GitRepositoryImpl", "getUser: $body")
                    return body.toUserDomain()
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

    override suspend fun getRepos(userName: String): List<Repos> {
        try {
            val response = apiService.getRepos(userName)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return body.map { it.toReposDomain() }
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

    override suspend fun getRepoDetail(owner: String, repo: String): RepoDetail {
        try {
            val response = apiService.getRepoDetail(owner, repo)
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

