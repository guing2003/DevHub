package com.delecrode.devhub.presentation.ui.forgot

data class ForgotPasswordState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val emailError: String? = null
)