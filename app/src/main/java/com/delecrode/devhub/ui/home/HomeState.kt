package com.delecrode.devhub.ui.home

import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit

data class HomeState(
    val isLoading: Boolean = false,
    val userForGit: UserForGit? = null,
    val userForFirebase: UserForFirebase? = null,
    val repos: List<Repos> = emptyList(),
    val error: String? = null,
)