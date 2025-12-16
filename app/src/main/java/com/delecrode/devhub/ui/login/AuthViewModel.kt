package com.delecrode.devhub.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun signIn(email: String, password: String) {

        viewModelScope.launch {
            _state.value = AuthState(isLoading = true)
            val emailValidation = validateEmail(email)
            if (emailValidation != null) {
                _state.value = _state.value.copy(
                    emailError = emailValidation,
                    passwordError = null,
                    error = null,
                    isLoading = false
                )
                return@launch
            }

            val passwordValidation = validatePassword(password)
            if (passwordValidation != null) {
                _state.value = _state.value.copy(
                    emailError = null,
                    passwordError = passwordValidation,
                    error = null,
                    isLoading = false
                )
                return@launch
            }

            when (val result = repository.signIn(email, password)) {
                is Result.Success -> {
                    _state.value = AuthState(isSuccess = true)
                }

                is Result.Error -> {
                    _state.value = AuthState(error = result.message, isLoading = false)

                }
            }
        }
    }


    fun clearState() {
        _state.value = AuthState()
    }

    fun clearEmailError() {
        _state.value = _state.value.copy(emailError = null)
    }

    fun clearPasswordError() {
        _state.value = _state.value.copy(passwordError = null)
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email é obrigatório"
            !isValidEmailFormat(email) -> "Email inválido"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Senha é obrigatória"
            password.length < 6 -> "Senha deve ter pelo menos 6 caracteres"
            !countPassword(password) -> "Senha deve conter pelo menos 1 letra e 1 número"
            else -> null
        }
    }

    private fun isValidEmailFormat(email: String): Boolean {
        return Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$").matches(email)
    }

    private fun countPassword(password: String): Boolean {
        return password.length >= 6
    }
}
