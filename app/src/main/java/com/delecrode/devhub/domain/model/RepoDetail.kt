package com.delecrode.devhub.domain.model

data class RepoDetail(
    val name: String,
    val html_url: String,
    val description: String?,
    val branches_url: String,
    val tags_url: String,
    val created_at: String,
    val updated_at: String,
    val pushed_at: String,
    val clone_url: String,
    val size: Int,
    val language: String,
    val forks_count: Int,
    val default_branch: String,
    val subscribers_count: Int
)

data class Languages(
    val languages: List<String>?
)

