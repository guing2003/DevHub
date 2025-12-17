package com.delecrode.devhub.presentation.ui.favoritos

import com.delecrode.devhub.domain.model.RepoFav

data class RepoFavState(
    val isLoading: Boolean = false,
    val repoFav: List<RepoFav> = emptyList(),
    val error: String? = null
)