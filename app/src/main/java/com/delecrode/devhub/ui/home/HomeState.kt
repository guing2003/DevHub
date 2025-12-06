package com.delecrode.devhub.ui.home

import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.User

data class HomeState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val repos: List<Repos> = emptyList(),
    val error: String? = null,
)