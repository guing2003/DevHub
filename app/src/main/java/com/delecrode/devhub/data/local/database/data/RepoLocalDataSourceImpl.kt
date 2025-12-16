package com.delecrode.devhub.data.local.database.data

import com.delecrode.devhub.data.local.database.dao.RepoDao
import com.delecrode.devhub.data.model.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

class RepoLocalDataSourceImpl(
    private val dao: RepoDao
) : RepoLocalDataSource {

    override suspend fun save(repo: RepoEntity) {
        dao.insert(repo)
    }

    override fun getAll(): Flow<List<RepoEntity>> =
        dao.getAll()

    override suspend fun delete(id: Int) {
        dao.delete(id)
    }
}
