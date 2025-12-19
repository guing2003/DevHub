package com.delecrode.devhub.viewModel

import com.delecrode.devhub.MainDispatcherRule
import com.delecrode.devhub.data.utils.Result
import com.delecrode.devhub.domain.model.Repos
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit
import com.delecrode.devhub.domain.useCase.AuthUseCase
import com.delecrode.devhub.domain.useCase.FetchUserDataUseCase
import com.delecrode.devhub.presentation.ui.profile.ProfileViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileTestes {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    val fetchUserData: FetchUserDataUseCase = mockk()
    val authUseCase: AuthUseCase = mockk()

    private lateinit var profileViewModel: ProfileViewModel

    @Before
    fun setup() {
        profileViewModel = ProfileViewModel(fetchUserData, authUseCase)
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

        profileViewModel.loadUserFromGit("teste userName")

        advanceUntilIdle()

        val state = profileViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.userForGit == userForGit)
        assert(state.error == null)

        coVerify(exactly = 1) {
            fetchUserData.loadUserFromGit(any())
        }
    }

    @Test
    fun `quando buscar um usuario do GitHub deve retornar erro`() = runTest {
        coEvery {
            fetchUserData.loadUserFromGit("")
        } returns Result.Error("Erro ao buscar usuario")

        profileViewModel.loadUserFromGit("")

        advanceUntilIdle()

        val state = profileViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.userForGit == null)
        assert(state.error == "Erro ao buscar usuario")

        coVerify(exactly = 1) {
            fetchUserData.loadUserFromGit(any())
        }
    }

    @Test
    fun `quando buscar um usuario do Firebase deve retornar sucesso`() =
        runTest {
            coEvery {
                fetchUserData.loadUserFromFirebase()
            } returns Result.Success(userForFirebase)

            profileViewModel.loadUserFromFirebase()
            advanceUntilIdle()

            val state = profileViewModel.uiState.value

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

        profileViewModel.loadUserFromFirebase()

        advanceUntilIdle()

        val state = profileViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.userForFirebase == UserForFirebase())
        assert(state.error == "Erro ao buscar usuario")

        coVerify(exactly = 1) {
            fetchUserData.loadUserFromFirebase()
        }
    }

    @Test
    fun `quando buscar um usuario do Firebase deve retornar sucesso e buscar dados do Git deve retornar sucesso e buscar os dados do repositorios deve retornar sucesso`() =
        runTest {
            coEvery {
                fetchUserData.loadUserFromFirebase()
            } returns Result.Success(userForFirebase)

            coEvery {
                fetchUserData.loadUserFromGit(userForFirebase.username)
            } returns Result.Success(userForGit)

            coEvery {
                fetchUserData.loadRepos(userForGit.login.let { "teste userName" })
            } returns Result.Success(repos)


            profileViewModel.loadProfile()
            advanceUntilIdle()

            val state = profileViewModel.uiState.value

            assert(state.userForFirebase == userForFirebase)
            assert(state.userForGit == userForGit)
            assert(state.repos == repos)
            assert(state.error == null)

            coVerify(exactly = 1) {
                fetchUserData.loadUserFromFirebase()
                fetchUserData.loadUserFromGit(userForFirebase.username)
                fetchUserData.loadRepos(any())
            }
        }


    @Test
    fun `quando buscar um usuario do Firebase deve retornar sucesso e buscar dados do Git deve retornar erro`() =
        runTest {
            coEvery {
                fetchUserData.loadUserFromFirebase()
            } returns Result.Success(userForFirebase)

            coEvery {
                fetchUserData.loadUserFromGit(userForFirebase.username)
            } returns Result.Error("Erro ao buscar usuario")

            profileViewModel.loadProfile()
            advanceUntilIdle()

            val state = profileViewModel.uiState.value

            assert(state.userForFirebase == userForFirebase)
            assert(state.userForGit == null)
            assert(state.error == "Erro ao buscar usuario")

            coVerify(exactly = 1) {
                fetchUserData.loadUserFromFirebase()
                fetchUserData.loadUserFromGit(userForFirebase.username)
            }
        }

    @Test
    fun `quando buscar um usuario do Firebase deve retornar sucesso e buscar dados do Git deve retornar sucesso e buscar os dados do repositorios deve retornar erro`() =
        runTest {
            coEvery {
                fetchUserData.loadUserFromFirebase()
            } returns Result.Success(userForFirebase)

            coEvery {
                fetchUserData.loadUserFromGit(userForFirebase.username)
            } returns Result.Success(userForGit)

            coEvery {
                fetchUserData.loadRepos(userForGit.login.let { "teste userName" })
            } returns Result.Error("Erro ao buscar repositorios")


            profileViewModel.loadProfile()
            advanceUntilIdle()

            val state = profileViewModel.uiState.value

            assert(state.userForFirebase == userForFirebase)
            assert(state.userForGit == userForGit)
            assert(state.repos.isEmpty())
            assert(state.error == "Erro ao buscar repositorios")

            coVerify(exactly = 1) {
                fetchUserData.loadUserFromFirebase()
                fetchUserData.loadUserFromGit(userForFirebase.username)
                fetchUserData.loadRepos(any())
            }
        }

    @Test
    fun `quando buscar os repositorios do usuario deve retornar sucesso`() = runTest {
        coEvery {
            fetchUserData.loadRepos("teste userName")
        } returns Result.Success(repos)

        profileViewModel.loadRepos("teste userName")

        advanceUntilIdle()

        val state = profileViewModel.uiState.value

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

        profileViewModel.loadRepos("teste userName")

        advanceUntilIdle()

        val state = profileViewModel.uiState.value

        assert(!state.isLoading)
        assert(state.repos.isEmpty())
        assert(state.error == "Erro ao buscar repositorios")

        coVerify(exactly = 1) {
            fetchUserData.loadRepos(any())
        }
    }
}