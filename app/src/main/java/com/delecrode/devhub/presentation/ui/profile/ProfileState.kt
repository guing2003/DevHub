package com.delecrode.devhub.presentation.ui.profile

import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit

data class ProfileState(
    val isLoading: Boolean = false,
    val userForFirebase: UserForFirebase = UserForFirebase(),
    val userForGit: UserForGit? = null,
    val repos: List<Repos> = emptyList(),
    val error: String? = null

)