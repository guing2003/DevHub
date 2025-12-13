package com.delecrode.devhub.ui.favoritos

import com.delecrode.devhub.domain.model.RepoFav
import kotlinx.coroutines.flow.Flow

data class RepoFavState(
    val isLoading: Boolean = false,
    val repoFav: List<RepoFav> = emptyList(),
    val error: String? = null
)