package com.delecrode.devhub.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "repositories")
data class RepoEntity(
    @PrimaryKey(autoGenerate = true)
    val idLocal: Long = 0,
    val id: Int,
    val name: String,
    val userName: String,
    val description: String,
    val url: String,
)
