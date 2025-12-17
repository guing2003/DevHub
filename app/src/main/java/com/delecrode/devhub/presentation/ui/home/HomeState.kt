package com.delecrode.devhub.presentation.ui.home

import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit

data class HomeState(
    val userForSearchGit: UserForGit? = null,
    val userForGit: UserForGit? = null,
    val userForFirebase: UserForFirebase? = null,
    val repos: List<Repos> = emptyList(),
    val searchText: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)