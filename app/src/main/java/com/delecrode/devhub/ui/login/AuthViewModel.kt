package com.delecrode.devhub.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.repository.AuthRepository
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

            try {
                val user = repository.signIn(email, password)
                _state.value = AuthState(
                    isSuccess = true,
                    userUid = user.uid,
                    isLoading = false
                )
                Log.i("AuthViewModel", "signIn: Usuario Logado ${user.uid}")
            } catch (e: Exception) {
                _state.value = AuthState(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }


    fun clearState(){
        _state.value = AuthState()
    }
}
