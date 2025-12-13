package com.delecrode.devhub.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProfileViewModel(private val userRepository: UserRepository, private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState

    init{
        getUserForFirebase()
    }

    fun getUserForFirebase() {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            try {
                val userForFirebase = userRepository.getUserForFirebase()
                _uiState.value = _uiState.value.copy(
                    userForFirebase = userForFirebase
                )

                if (userForFirebase.username.isNotBlank()) {
                    getUserForGit(userForFirebase.username)
                    getRepos(userForFirebase.username)
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun getUserForGit(userName: String) {
        viewModelScope.launch {
            try {
                val userForGit = userRepository.getUserForGitHub(userName)
                _uiState.value = _uiState.value.copy(
                    userForGit = userForGit,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun getRepos(userName: String) {
        viewModelScope.launch {
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
                Log.e("ProfileViewModel", "Erro ao fazer logout", e)
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun clearState(){
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = null
        )
    }

}
