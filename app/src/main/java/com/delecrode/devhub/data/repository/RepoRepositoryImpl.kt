package com.delecrode.devhub.data.repository

import com.delecrode.devhub.data.local.dataStore.AuthLocalDataSource
import com.delecrode.devhub.data.local.database.data.RepoLocalDataSource
import com.delecrode.devhub.data.mapper.toDomain
import com.delecrode.devhub.data.mapper.toEntity
import com.delecrode.devhub.data.mapper.toLanguagesDomain
import com.delecrode.devhub.data.mapper.toRepoDetailDomain
import com.delecrode.devhub.data.remote.webApi.service.RepoApiService
import com.delecrode.devhub.domain.model.Languages
import com.delecrode.devhub.domain.model.RepoDetail
import com.delecrode.devhub.domain.model.RepoFav
import com.delecrode.devhub.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepoRepositoryImpl(val repoApi: RepoApiService,  private val localDataSource: RepoLocalDataSource, private val authLocalDataSource: AuthLocalDataSource) : RepoRepository {

    override suspend fun save(repo: RepoFav) {
        localDataSource.save(repo.toEntity())
    }

    override fun getAll(): Flow<List<RepoFav>> =
        localDataSource.getAll()
            .map { list -> list.map { it.toDomain() } }

    override fun getUserName(): Flow<String?> =
        authLocalDataSource.getUserName()

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

    override suspend fun getLanguagesRepo(owner: String, repo: String) : Languages{
        return try {
            val response = repoApi.getRepoLanguages(owner, repo)
            if (response.isSuccessful) {
                val body = response.body()
                body?.toLanguagesDomain() ?: Languages(emptyList())
            } else {
                Languages(emptyList())
            }
        } catch (e: Exception) {
            Languages(emptyList())
        }
    }
}