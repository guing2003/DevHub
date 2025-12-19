package com.delecrode.devhub.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.data.utils.Result
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit
import com.delecrode.devhub.domain.useCase.AuthUseCase
import com.delecrode.devhub.domain.useCase.FetchUserDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val fetchUserData: FetchUserDataUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState


    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val firebaseResult = loadUserFromFirebase()
            if (firebaseResult is Result.Success) {
                val gitResult = loadUserFromGit(firebaseResult.data.username)
                if (gitResult is Result.Success && gitResult.data.login != null) {
                    loadRepos(gitResult.data.login)
                }
            }
        }
    }

    suspend fun loadUserFromFirebase(): Result<UserForFirebase> {
        val result = fetchUserData.loadUserFromFirebase()
        when (result) {
            is Result.Success -> _uiState.update { it.copy(userForFirebase = result.data, isLoading = false) }
            is Result.Error -> _uiState.update { it.copy(error = result.message, isLoading = false) }
        }
        return result
    }

    suspend fun loadUserFromGit(username: String): Result<UserForGit> {
        val result = fetchUserData.loadUserFromGit(username)
        when (result) {
            is Result.Success -> _uiState.update { it.copy(userForGit = result.data, isLoading = false) }
            is Result.Error -> _uiState.update { it.copy(error = result.message, isLoading = false) }
        }
        return result
    }


    suspend fun loadRepos(username: String) {
        when (val result = fetchUserData.loadRepos(username)) {
            is Result.Success ->
                _uiState.update { it.copy(repos = result.data, isLoading = false) }

            is Result.Error ->
                _uiState.update { it.copy(error = result.message, isLoading = false) }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                authUseCase.signOut()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message, isLoading = false)
                }
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

