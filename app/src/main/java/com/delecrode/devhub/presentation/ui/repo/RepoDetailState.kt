package com.delecrode.devhub.presentation.ui.repo

import com.delecrode.devhub.domain.model.RepoDetail

data class RepoState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userName: String? = null,
    val repo: RepoDetail? = null,
    val languages: List<String>? = null,
    val isFavorite: Boolean = false
)