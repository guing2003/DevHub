package com.delecrode.devhub.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.UserRepository
import com.delecrode.devhub.data.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState


    fun getUserForFirebase() {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            when (val result = userRepository.getUserForFirebase()) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        userForFirebase = result.data,
                        isLoading = false
                    )
                    getUserForGit(result.data.username)
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getUserForGit(userName: String) {
        viewModelScope.launch {
            when (val result = userRepository.getUserForGitHub(userName)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        userForGit = result.data,
                        isLoading = false
                    )
                    getRepos(userName)
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getRepos(userName: String) {
        viewModelScope.launch {
            when (val result = userRepository.getRepos(userName)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        repos = result.data,
                        isLoading = false
                    )
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                authRepository.signOut()
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Erro ao fazer logout", e)
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun clearState() {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = null
        )
    }
}

