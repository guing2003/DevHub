package com.delecrode.devhub.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.delecrode.devhub.data.local.database.dao.RepoDao
import com.delecrode.devhub.data.model.entity.RepoEntity

@Database(
    entities = [RepoEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
