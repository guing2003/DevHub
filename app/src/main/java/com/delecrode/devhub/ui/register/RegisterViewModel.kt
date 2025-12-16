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

    fun signUp(name: String, username: String, email: String, password: String) {
        viewModelScope.launch {
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
}