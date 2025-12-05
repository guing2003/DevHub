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