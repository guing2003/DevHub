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
import com.delecrode.devhub.utils.Result
import com.delecrode.devhub.utils.mapHttpError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class RepoRepositoryImpl(
    val repoApi: RepoApiService,
    private val localDataSource: RepoLocalDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : RepoRepository {

    override suspend fun save(repo: RepoFav) {
        localDataSource.save(repo.toEntity())
    }

    override fun getAll(): Flow<List<RepoFav>> =
        localDataSource.getAll()
            .map { list -> list.map { it.toDomain() } }

    override fun getUserName(): Flow<String?> =
        authLocalDataSource.getUserName()

    override suspend fun getRepoDetail(owner: String, repo: String): Result<RepoDetail> {
        return try {
            val response = repoApi.getRepoDetail(owner, repo)
            if (response.isSuccessful) {
                val body = response.body()
                    ?: return Result.Error("Dados do repositório indisponíveis")
                Result.Success(body.toRepoDetailDomain())
            } else {
                Result.Error(mapHttpError(response.code()))
            }
        } catch (e: IOException) {
            Result.Error("Sem conexão com a internet")
        }
    }

    override suspend fun getLanguagesRepo(
        owner: String,
        repo: String
    ): Result<Languages> {
        return try {
            val response = repoApi.getRepoLanguages(owner, repo)

            if (response.isSuccessful) {
                val body = response.body()
                    ?: return Result.Error("Linguagens indisponíveis")

                Result.Success(body.toLanguagesDomain())
            } else {
                Result.Error(mapHttpError(response.code()))
            }

        } catch (e: IOException) {
            Result.Error("Sem conexão com a internet")
        }
    }

}