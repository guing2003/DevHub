package com.delecrode.devhub.data.repository

import android.util.Log
import com.delecrode.devhub.data.local.dataStore.AuthLocalDataSource
import com.delecrode.devhub.data.mapper.toReposDomain
import com.delecrode.devhub.data.mapper.toUserDomain
import com.delecrode.devhub.data.model.dto.UserForFirebaseDto
import com.delecrode.devhub.data.remote.firebase.UserExtraData
import com.delecrode.devhub.data.remote.webApi.service.UserApiService
import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit
import com.delecrode.devhub.domain.repository.UserRepository
import kotlinx.coroutines.flow.first

class UserRepositoryImpl(
    private val userApi: UserApiService,
    private val userExtraData: UserExtraData,
    private val authLocalDataSource: AuthLocalDataSource
) : UserRepository {

    override suspend fun getUserForGitHub(userName: String): UserForGit {
        try {
            val response = userApi.getUser(userName)
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

    override suspend fun getUserForFirebase(): UserForFirebase {
        try {
            val uid = authLocalDataSource.getUID().first()
            Log.i("UserRepositoryImpl", "getUserForFirebase (UID Real): $uid")
            if (uid != null) {
                val response = userExtraData.getUser(uid)
                if (response.exists()) {
                    val body = response.toObject(UserForFirebaseDto::class.java)?.toUserDomain()
                    val userName = body?.username
                    if (userName != null) {
                        authLocalDataSource.saveUserName(userName)
                    }
                    if (body != null) {
                        return body
                    } else {
                        throw Exception("Resposta vazia do servidor")
                    }
                } else {
                    throw Exception("Usuário não encontrado")
                }
            } else {
                return UserForFirebase()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getRepos(userName: String): List<Repos> {
        try {
            val response = userApi.getReposForUser(userName)
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
}
