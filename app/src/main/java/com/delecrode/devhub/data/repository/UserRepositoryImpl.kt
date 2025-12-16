package com.delecrode.devhub.data.repository

import android.util.Log
import com.delecrode.devhub.data.local.dataStore.AuthLocalDataSource
import com.delecrode.devhub.data.mapper.toReposDomain
import com.delecrode.devhub.data.mapper.toUserFirebaseDomain
import com.delecrode.devhub.data.mapper.toUserGitDomain
import com.delecrode.devhub.data.model.dto.UserForFirebaseDto
import com.delecrode.devhub.data.remote.firebase.UserExtraData
import com.delecrode.devhub.data.remote.webApi.service.UserApiService
import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit
import com.delecrode.devhub.domain.repository.UserRepository
import com.delecrode.devhub.utils.Result
import com.delecrode.devhub.utils.mapHttpError
import kotlinx.coroutines.flow.first
import java.io.IOException

class UserRepositoryImpl(
    private val userApi: UserApiService,
    private val userExtraData: UserExtraData,
    private val authLocalDataSource: AuthLocalDataSource
) : UserRepository {

    override suspend fun getUserForGitHub(userName: String): Result<UserForGit> {
        return try {
            val response = userApi.getUser(userName)
            if (response.isSuccessful) {
                val body = response.body()
                    ?: return Result.Error("Dados do usuário indisponíveis")
                Result.Success(body.toUserGitDomain())
            } else {
               Result.Error(mapHttpError(response.code()))
            }
        } catch (e: IOException) {
            Result.Error("Sem conexão com a internet")
        }
    }

    override suspend fun getUserForFirebase(): Result<UserForFirebase> {
        return try {
            val uid = authLocalDataSource.getUID().first()
                ?: return Result.Error("Usuário não autenticado")

            val snapshot = userExtraData.getUser(uid)

            if (!snapshot.exists()) {
                return Result.Error("Usuário não encontrado")
            }

            val user = snapshot
                .toObject(UserForFirebaseDto::class.java)
                ?.toUserFirebaseDomain()
                ?: return Result.Error("Dados do usuário inválidos")

            authLocalDataSource.saveUserName(user.username)

            Result.Success(user)

        } catch (e: Exception) {
            Result.Error("Erro ao buscar dados do usuário")
        }
    }


    override suspend fun getRepos(userName: String): Result<List<Repos>> {
        return try {
            val response = userApi.getReposForUser(userName)
            if (response.isSuccessful) {
                val body = response.body()
                    ?: return Result.Error("Dados dos repositórios indisponíveis")
                Result.Success(body.map { it.toReposDomain() })
            } else {
                Result.Error(mapHttpError(response.code()))
            }
        } catch (e: IOException) {
            Result.Error("Sem conexão com a internet")
        }
    }
}
