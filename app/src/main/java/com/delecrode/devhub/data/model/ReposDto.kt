package com.delecrode.devhub.data.model

data class ReposDto(
    val id: Int,
    val node_id: String,
    val name: String,
    val full_name: String,
    val private: Boolean,
    val description: String,
    val url: String,
    val created_at: String,
    val updated_at: String,
    val pushed_at: String,
    val clone_url: String
)


data class RepoDetailDto(
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


class LanguagesDto : HashMap<String, Int>()
