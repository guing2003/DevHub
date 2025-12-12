package com.delecrode.devhub.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    fun getUser(userName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val user = repository.getUserForGitHub(userName)
                _uiState.value = _uiState.value.copy(
                    user = user,
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

    fun getRepos(userName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val repos: List<Repos> = repository.getRepos(userName)
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

    fun clearStates(){
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = null
        )
    }
}
