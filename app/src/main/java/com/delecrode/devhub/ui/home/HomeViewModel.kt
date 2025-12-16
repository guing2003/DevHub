package com.delecrode.devhub.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.UserRepository
import com.delecrode.devhub.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    fun getUserForSearchGit(userName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            when (val result = userRepository.getUserForGitHub(userName)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        userForGit = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun getUserForGit(userName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            when (val result = userRepository.getUserForGitHub(userName)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        userForGit = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun getUserForFirebase() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            when (val result = userRepository.getUserForFirebase()) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        userForFirebase = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }

        }
    }

    fun getRepos(userName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            when (val result = userRepository.getRepos(userName)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        repos = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
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
                    Log.e("HomeViewModel", "Erro ao fazer logout", e)
                    _uiState.value = _uiState.value.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }

        fun clearStates() {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = null
            )
        }
    }

