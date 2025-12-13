package com.delecrode.devhub.data.local.database.data

import com.delecrode.devhub.data.model.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

interface RepoLocalDataSource {

    suspend fun save(repo: RepoEntity)

    fun getAll(): Flow<List<RepoEntity>>
}
