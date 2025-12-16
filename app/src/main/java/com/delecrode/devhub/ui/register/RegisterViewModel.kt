package com.delecrode.devhub.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun signUp(name: String, username: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            validateName(name)?.let {
                _state.value = _state.value.copy(nameError = it)
                return@launch
            }

            validateUsername(username)?.let {
                _state.value = _state.value.copy(usernameError = it)
                return@launch
            }
            val emailValidation = validateEmail(email)
            if (emailValidation != null) {
                _state.value = _state.value.copy(
                    emailError = emailValidation,
                    passwordError = null,
                    error = null
                )
                return@launch
            }

            val passwordValidation = validatePassword(password)
            if (passwordValidation != null) {
                _state.value = _state.value.copy(
                    emailError = null,
                    passwordError = passwordValidation,
                    error = null
                )
                return@launch
            }
            validateConfirmPassword(password, confirmPassword)?.let {
                _state.value = _state.value.copy(confirmPasswordError = it)
                return@launch
            }

            when (val result = repository.signUp(name, username, email, password)) {
                is Result.Success -> {
                    _state.value = RegisterState(isSuccess = true)
                }

                is Result.Error -> {
                    _state.value = RegisterState(error = result.message)
                }
            }
        }
    }


    fun clearState() {
        _state.value = RegisterState()

    }

    private fun validateName(name: String): String? =
        if (name.isBlank()) "Nome é obrigatório" else null

    private fun validateUsername(username: String): String? =
        if (username.isBlank()) "Username é obrigatório" else null

    private fun validateEmail(email: String): String? =
        when {
            email.isBlank() -> "Email é obrigatório"
            !Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$").matches(email) ->
                "Email inválido"

            else -> null
        }

    private fun validatePassword(password: String): String? =
        when {
            password.isBlank() -> "Senha é obrigatória"
            password.length < 6 -> "Senha deve ter pelo menos 6 caracteres"
            !password.any { it.isDigit() } ->
                "Senha deve conter pelo menos 1 número"

            else -> null
        }

    private fun validateConfirmPassword(
        password: String,
        confirmPassword: String
    ): String? =
        when {
            confirmPassword.isBlank() -> "Confirme a senha"
            password != confirmPassword -> "As senhas devem ser iguais"
            else -> null
        }




    fun clearEmailError() {
        _state.value = _state.value.copy(emailError = null)
    }

    fun clearPasswordError() {
        _state.value = _state.value.copy(passwordError = null)
    }

}