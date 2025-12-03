package com.delecrode.devhub.domain.model

data class User(
    val login: String?,
    val avatar_url : String?,
    val url : String?,
    val name: String?,
    val bio: String?,
    val repos_url : String?
)
