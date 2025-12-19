package com.delecrode.devhub.presentation.ui.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.data.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            val emailValidation = validateEmail(email)
            if (emailValidation != null) {
                _state.value = _state.value.copy(
                    emailError = emailValidation,
                    error = null,
                    isLoading = false
                )
                return@launch
            }
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            when (val result = authRepository.forgotPassword(email)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                }

                is Result.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun clearState() {
        _state.value = ForgotPasswordState()
    }

    fun clearEmailError() {
        _state.value = _state.value.copy(emailError = null)
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email é obrigatório"
            !isValidEmailFormat(email) -> "Email inválido"
            else -> null
        }
    }

    private fun isValidEmailFormat(email: String): Boolean {
        return Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$").matches(email)
    }

}