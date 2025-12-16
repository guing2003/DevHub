package com.delecrode.devhub.ui.forgot

data class ForgotPasswordState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val emailError: String? = null
)