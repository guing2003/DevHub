import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delecrode.devhub.data.utils.Result
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit
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

    fun loadSearchUser(username: String) {
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
            val firebaseResult = loadUserFromFirebase()
            if (firebaseResult is Result.Success) {
                loadUserFromGit(firebaseResult.data.username)
            }
        }
    }

    suspend fun loadUserFromFirebase(): Result<UserForFirebase> {
        _uiState.update { it.copy(isLoading = true, error = null) }

        val result = fetchUserData.loadUserFromFirebase()
        when (result) {
            is Result.Success -> _uiState.update { it.copy(userForFirebase = result.data, isLoading = false) }
            is Result.Error -> _uiState.update { it.copy(error = result.message, isLoading = false) }
        }
        return result
    }

    suspend fun loadUserFromGit(username: String): Result<UserForGit> {
        _uiState.update { it.copy(isLoading = true, error = null) }

        val result = fetchUserData.loadUserFromGit(username)
        when (result) {
            is Result.Success -> _uiState.update { it.copy(userForGit = result.data, isLoading = false) }
            is Result.Error -> _uiState.update { it.copy(error = result.message, isLoading = false) }
        }
        return result
    }




    fun loadRepos(username: String) {
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
