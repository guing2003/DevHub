package com.delecrode.devhub.viewModel

import com.delecrode.devhub.MainDispatcherRule
import com.delecrode.devhub.data.utils.Result
import com.delecrode.devhub.domain.model.UserForGit
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.UserRepository
import com.delecrode.devhub.presentation.ui.register.RegisterViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterTestes {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val authRepository: AuthRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        viewModel = RegisterViewModel(authRepository, userRepository)
    }

    val userForGit = UserForGit(
        login = "teste userName",
        avatar_url = "teste avatar",
        url = "teste url",
        name = "teste nome",
        bio = "teste bio",
        repos_url = "teste repos url"
    )

    @Test
    fun `quando validar um usuario do GitHub deve retornar sucesso`() = runTest {
        coEvery {
            userRepository.getUserForGitHub("teste userName")
        } returns Result.Success(userForGit)

        viewModel.validateGithubUsername("teste userName")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(!state.isLoading)
        assert(state.usernameSuccess)
        assert(state.usernameError == null)
        assert(state.error == null)

        coVerify(exactly = 1) {
            userRepository.getUserForGitHub("teste userName")
        }
    }

    @Test
    fun `quando validar um usuario que não existe no GitHub deve retornar erro`() = runTest {
        coEvery {
            userRepository.getUserForGitHub("teste de erro")
        } returns Result.Error("Usuário não existe no GitHub")

        viewModel.validateGithubUsername("teste de erro")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(!state.isLoading)
        assert(!state.isSuccess)
        assert(state.usernameError == "Usuário não existe no GitHub")

        coVerify(exactly = 1) {
            userRepository.getUserForGitHub(any())
        }
    }

    @Test
    fun `quando validar um usuario vazio no GitHub deve retornar erro`() = runTest {
        viewModel.validateGithubUsername("")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(!state.isLoading)
        assert(!state.isSuccess)
        assert(state.usernameError == "Username é obrigatório")

        coVerify(exactly = 0) {
            userRepository.getUserForGitHub(any())
        }
    }
}