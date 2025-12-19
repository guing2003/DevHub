import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.data.utils.Result
import com.delecrode.devhub.domain.useCase.AuthUseCase
import com.delecrode.devhub.domain.useCase.FetchUserDataUseCase
import com.delecrode.devhub.presentation.ui.home.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(
    private val fetchUserData: FetchUserDataUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    fun onSearchTextChange(value: String) {
        _uiState.update { it.copy(searchText = value) }
    }

    fun onSearchClick() {
        val search = uiState.value.searchText
        if (search.isBlank()) return

        loadSearchUser(search)
        loadRepos(search)
    }

    private fun loadSearchUser(username: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = fetchUserData.loadUserFromGit(username)) {
                is Result.Success ->
                    _uiState.update {
                        it.copy(userForSearchGit = result.data, isLoading = false)
                    }

                is Result.Error ->
                    _uiState.update {
                        it.copy(error = result.message, isLoading = false)
                    }
            }
        }
    }

    fun loadHome() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val firebase = fetchUserData.loadUserFromFirebase()) {
                is Result.Success -> {
                    _uiState.update { it.copy(userForFirebase = firebase.data) }

                    when (val git = fetchUserData.loadUserFromGit(firebase.data.username)) {
                        is Result.Success ->
                            _uiState.update {
                                it.copy(userForGit = git.data, isLoading = false)
                            }

                        is Result.Error ->
                            _uiState.update {
                                it.copy(error = git.message, isLoading = false)
                            }
                    }
                }

                is Result.Error ->
                    _uiState.update {
                        it.copy(error = firebase.message, isLoading = false)
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

    fun clearUi() {
        _uiState.update {
            it.copy(
                searchText = "",
                userForSearchGit = null,
                repos = emptyList(),
                error = null
            )
        }
    }

    fun clearStates() {
        _uiState.update {
            it.copy(
                userForFirebase = null,
                userForGit = null,
                userForSearchGit = null
            )
        }
    }
}
