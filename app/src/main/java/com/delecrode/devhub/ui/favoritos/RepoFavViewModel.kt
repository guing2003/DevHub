package com.delecrode.devhub.ui.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.repository.RepoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepoFavViewModel(private val repoRepository: RepoRepository): ViewModel(){
    
    private val _uiState = MutableStateFlow(RepoFavState())
    val uiState: StateFlow<RepoFavState> = _uiState

    init {
        getAllRepoFav()
    }
    
    fun getAllRepoFav(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                repoRepository.getAll().collect { repos ->
                    _uiState.value = _uiState.value.copy(
                        repoFav = repos,
                        isLoading = false
                    )
                }
            }catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}