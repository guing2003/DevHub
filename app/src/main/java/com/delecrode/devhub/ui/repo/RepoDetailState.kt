package com.delecrode.devhub.ui.repo

import com.delecrode.devhub.domain.model.RepoDetail

data class RepoState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val repo: RepoDetail? = null
)