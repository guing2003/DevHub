package com.delecrode.devhub.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.UserRepository
import com.delecrode.devhub.data.utils.Result
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

            when (val firebase = fetchUserData.loadUserFromFirebase()) {
                is Result.Success -> {
                    val firebaseUser = firebase.data
                    _uiState.update { it.copy(userForFirebase = firebaseUser) }

                    when (val git = fetchUserData.loadUserFromGit(firebaseUser.username)) {
                        is Result.Success -> {
                            val gitUser = git.data
                            _uiState.update { it.copy(userForGit = gitUser) }

                            loadRepos(firebaseUser.username)

                            _uiState.update { it.copy(isLoading = false) }
                        }

                        is Result.Error -> {
                            _uiState.update {
                                it.copy(error = git.message, isLoading = false)
                            }
                        }
                    }
                }

                is Result.Error -> {
                    _uiState.update { it.copy(error = firebase.message, isLoading = false) }
                }
            }
        }
    }


    private fun loadRepos(username: String) {
        viewModelScope.launch {
            when (val result = fetchUserData.loadRepos(username)) {
                is Result.Success ->
                    _uiState.update { it.copy(repos = result.data) }

                is Result.Error ->
                    _uiState.update { it.copy(error = result.message) }
            }
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

