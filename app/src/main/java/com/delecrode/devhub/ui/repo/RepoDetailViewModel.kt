package com.delecrode.devhub.ui.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.repository.RepoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepoDetailViewModel(val repository: RepoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RepoState())
    val uiState: StateFlow<RepoState> = _uiState

    fun getRepoDetail(owner: String, repo: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val repoDetail = repository.getRepoDetail(owner, repo)
                _uiState.value = _uiState.value.copy(
                    repo = repoDetail,
                    isLoading = false
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false,
                )
            }
        }
    }

    fun getLanguagesForRepo(owner: String, repo: String){
        viewModelScope.launch {
            try{
                val result = repository.getLanguagesRepo(owner, repo)
                _uiState.value = _uiState.value.copy(
                    languages = result.languages
                )
            }catch (e: Exception){
                 _uiState.value = _uiState.value.copy(
                    languages = emptyList()
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