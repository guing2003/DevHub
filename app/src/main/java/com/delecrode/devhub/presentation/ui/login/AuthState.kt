package com.delecrode.devhub.presentation.ui.login

data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val userUid: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
) {
    val hasValidationErrors: Boolean
        get() = emailError != null || passwordError != null

    val canLogin: Boolean
        get() = !isLoading && !hasValidationErrors && error == null
}
