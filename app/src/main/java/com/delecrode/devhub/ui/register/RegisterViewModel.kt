package com.delecrode.devhub.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun signUp(name: String, username: String, email: String, password: String) {
        _state.value = RegisterState(isLoading = true)
        viewModelScope.launch {
            try{
                val response  = repository.signUp(name, username, email, password)
                _state.value = RegisterState(
                    isSuccess = response,
                    isLoading = false
                )
            }catch (e: Exception){
                _state.value = RegisterState(
                    error = e.message,
                    isLoading = false
                )
            }
        }

    }

    fun clearState() {
        _state.value = RegisterState()

    }
}