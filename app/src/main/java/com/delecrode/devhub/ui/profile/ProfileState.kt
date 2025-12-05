package com.delecrode.devhub.ui.profile

import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val repos: List<Repos> = emptyList(),
    val error: String? = null,
)