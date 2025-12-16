package com.delecrode.devhub.ui.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.domain.model.RepoFav
import com.delecrode.devhub.domain.repository.RepoRepository
import com.delecrode.devhub.utils.Result
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
            when (val result = repository.getRepoDetail(owner, repo)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        repo = result.data,
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

    fun getLanguagesForRepo(owner: String, repo: String) {
        viewModelScope.launch {
            when (val result = repository.getLanguagesRepo(owner, repo)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        languages = result.data.languages,
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

    fun clearState() {
        _uiState.value = _uiState.value.copy(
            repo = null,
            languages = emptyList(),
            isLoading = false,
            error = null
        )
    }


    fun favoriteRepo(id: Int, owner: String, name: String, description: String, url: String) {
        viewModelScope.launch {
            repository.save(
                RepoFav(
                    id = id,
                    name = name,
                    userName = owner,
                    description = description,
                    url = url
                )
            )
        }
    }
}
