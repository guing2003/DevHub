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
        _state.value = AuthState(isLoading = true)
        viewModelScope.launch {
            _state.value = AuthState(isLoading = true)

           when (val result = repository.signIn(email, password)) {
                is Result.Success -> {
                    _state.value = AuthState(isSuccess = true)
                }
                is Result.Error-> {
                    _state.value = AuthState(error = result.message)
                }
            }
        }
    }


    fun clearState(){
        _state.value = AuthState()
    }
}
