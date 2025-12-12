package com.delecrode.devhub.ui.login

data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val userUid: String? = null
)
