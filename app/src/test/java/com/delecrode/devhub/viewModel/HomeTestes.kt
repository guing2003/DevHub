package com.delecrode.devhub.viewModel

import HomeViewModel
import com.delecrode.devhub.MainDispatcherRule
import com.delecrode.devhub.data.utils.Result
import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit
import com.delecrode.devhub.domain.useCase.AuthUseCase
import com.delecrode.devhub.domain.useCase.FetchUserDataUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeTestes {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    val fetchUserData: FetchUserDataUseCase = mockk()
    val authUseCase: AuthUseCase = mockk()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(fetchUserData, authUseCase)
    }

    val userForGit = UserForGit(
        login = "teste userName",
        avatar_url = "teste avatar",
        url = "teste url",
        name = "teste nome",
        bio = "teste bio",
        repos_url = "teste repos url"
    )

    val userForFirebase = UserForFirebase(
        fullName = "teste full name",
        username = "teste username",
        email = "teste email"
    )

    val repos = listOf(
        Repos(
            id = 1,
            node_id = "teste node id",
            name = "teste name",
            full_name = "teste full name",
            private = false,
            description = "teste description",
            url = "teste url",
            created_at = "teste created at",
            updated_at = "teste updated at",
            pushed_at = "teste pushed at",
            clone_url = "teste clone url",
        )
    )

    @Test
    fun `quando buscar um usuario do GitHub deve retornar sucesso`() = runTest {
        coEvery {
            fetchUserData.loadUserFromGit("teste userName")
        } returns Result.Success(userForGit)

        homeViewModel.loadUserFromGit("teste userName")

        advanceUntilIdle()

        val state = homeViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.userForGit == userForGit)
        assert(state.error == null)

        coVerify(exactly = 1) {
            fetchUserData.loadUserFromGit(any())
        }
    }


    @Test
    fun `quando buscar um usuario do GitHub falhar deve retornar erro`() = runTest {
        coEvery {
            fetchUserData.loadUserFromGit("teste userName")
        } returns Result.Error("Erro ao buscar usuario")

        homeViewModel.loadSearchUser("teste userName")

        advanceUntilIdle()

        val state = homeViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.userForGit == null)
        assert(state.error == "Erro ao buscar usuario")

        coVerify(exactly = 1) {
            fetchUserData.loadUserFromGit(any())
        }
    }

    @Test
    fun `quando buscar um usuario do Firebase deve retornar sucesso e buscar dados do git e retornar sucesso`() =
        runTest {
            coEvery {
                fetchUserData.loadUserFromFirebase()
            } returns Result.Success(userForFirebase)

            homeViewModel.loadUserFromFirebase()
            advanceUntilIdle()

            val state = homeViewModel.uiState.value

            assert(state.userForFirebase == userForFirebase)
            assert(state.error == null)

            coVerify(exactly = 1) {
                fetchUserData.loadUserFromFirebase()
            }
        }

    @Test
    fun `quando buscar um usuario do Firebase falhar deve retornar erro`() = runTest {
        coEvery {
            fetchUserData.loadUserFromFirebase()
        } returns Result.Error("Erro ao buscar usuario")

        homeViewModel.loadHome()

        advanceUntilIdle()

        val state = homeViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.userForFirebase == null)
        assert(state.error == "Erro ao buscar usuario")

        coVerify(exactly = 1) {
            fetchUserData.loadUserFromFirebase()
        }
    }

    @Test
    fun `quando buscar um usuario do Firebase deve retornar sucesso e buscar dados do git e falhar`() =
        runTest {
            coEvery {
                fetchUserData.loadUserFromFirebase()
            } returns Result.Success(userForFirebase)

            coEvery {
                fetchUserData.loadUserFromGit(userForFirebase.username)
            } returns Result.Error("Erro ao buscar usuario")

            homeViewModel.loadHome()
            advanceUntilIdle()

            val state = homeViewModel.uiState.value

            assert(state.userForFirebase == userForFirebase)
            assert(state.userForGit == null)
            assert(state.error == "Erro ao buscar usuario")

            coVerify(exactly = 1) {
                fetchUserData.loadUserFromFirebase()
                fetchUserData.loadUserFromGit(userForFirebase.username)
            }
        }

    @Test
    fun `quando buscar os repositorios do usuario deve retornar sucesso`() = runTest {
        coEvery {
            fetchUserData.loadRepos("teste userName")
        } returns Result.Success(repos)

        homeViewModel.loadRepos("teste userName")

        advanceUntilIdle()

        val state = homeViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.repos == repos)
        assert(state.error == null)

        coVerify(exactly = 1) {
            fetchUserData.loadRepos(any())
        }
    }

    @Test
    fun `quando buscar os repositorios do usuario falhar deve retornar erro`() = runTest {
        coEvery {
            fetchUserData.loadRepos("teste userName")
        } returns Result.Error("Erro ao buscar repositorios")

        homeViewModel.loadRepos("teste userName")

        advanceUntilIdle()

        val state = homeViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.repos.isEmpty())
        assert(state.error == "Erro ao buscar repositorios")

        coVerify(exactly = 1) {
            fetchUserData.loadRepos(any())
        }
    }


    @Test
    fun `quando buscar um usuario atraves da pesquisa deve retornar sucesso`() = runTest {
        coEvery {
            fetchUserData.loadUserFromGit("teste userName")
        } returns Result.Success(userForGit)

        homeViewModel.loadSearchUser("teste userName")

        advanceUntilIdle()

        val state = homeViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.userForSearchGit == userForGit)
        assert(state.error == null)

        coVerify(exactly = 1) {
            fetchUserData.loadUserFromGit(any())
        }
    }

    @Test
    fun `quando buscar um usuario atraves da pesquisa falhar deve retornar erro`() = runTest {
        coEvery {
            fetchUserData.loadUserFromGit("teste userName")
        } returns Result.Error("Erro ao buscar usuario")

        homeViewModel.loadUserFromGit("teste userName")

        advanceUntilIdle()

        val state = homeViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.userForSearchGit == null)
        assert(state.error == "Erro ao buscar usuario")

        coVerify(exactly = 1) {
            fetchUserData.loadUserFromGit(any())
        }
    }
}