package com.delecrode.devhub.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.UserRepository
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
            try {
                val user = userRepository.getUserForGitHub(userName)
                _uiState.value = _uiState.value.copy(
                    userForSearchGit = user,
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Erro ao buscar usuário", e)
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun getUserForGit(userName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val user = userRepository.getUserForGitHub(userName)
                _uiState.value = _uiState.value.copy(
                    userForGit = user,
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Erro ao buscar usuário", e)
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun getUserForFirebase() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val user = userRepository.getUserForFirebase()
                _uiState.value = _uiState.value.copy(
                    userForFirebase = user,
                    isLoading = false
                )
                Log.i("HomeViewModel", "getUserForFirebase: $user")
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun getRepos(userName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val repos: List<Repos> = userRepository.getRepos(userName)
                _uiState.value = _uiState.value.copy(
                    repos = repos,
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Erro ao buscar repositórios", e)
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
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
