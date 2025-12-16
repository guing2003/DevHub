package com.delecrode.devhub.ui.register

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,

    val nameError: String? = null,
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
) {
    val hasValidationErrors: Boolean
        get() = emailError != null || passwordError != null

    val canRegister: Boolean
        get() = !isLoading && !hasValidationErrors && error == null
}

