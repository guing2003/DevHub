package com.delecrode.devhub

import com.delecrode.devhub.data.utils.Result
import com.delecrode.devhub.domain.model.UserAuth
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.presentation.ui.login.AuthViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginTestes {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val authRepository: AuthRepository = mockk()
    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        viewModel = AuthViewModel(authRepository)
    }

    //Testes de ViewModel
    @Test
    fun `quando signIn sucesso deve atualizar state com uid e sucesso`() = runTest {
        val userAuth = UserAuth(
            uid = "uid_123",
            email = "teste@email.com"
        )

        coEvery {
            authRepository.signIn(any(), any())
        } returns Result.Success(userAuth)

        viewModel.signIn("teste@email.com", "Teste123")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(state.isSuccess)
        assert(state.userUid == "uid_123")
        assert(!state.isLoading)
        assert(state.error == null)

        coVerify(exactly = 1) {
            authRepository.signIn(any(), any())
        }
    }

    @Test
    fun `quando email invalido na validacao local deve atualizar state com erro no email`() =
        runTest {

            viewModel.signIn("teste", "Teste123")

            advanceUntilIdle()

            val state = viewModel.state.value

            assert(!state.isSuccess)
            assert(!state.isLoading)
            assert(state.userUid == null)
            assert(state.emailError == "Email inválido")

            coVerify(exactly = 0) {
                authRepository.signIn(any(), any())
            }
        }

    @Test
    fun `quando email vazio na validacao local deve atualizar state com erro no email`() = runTest {

        viewModel.signIn("", "Teste123")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(!state.isSuccess)
        assert(!state.isLoading)
        assert(state.userUid == null)
        assert(state.emailError == "Email é obrigatório")

        coVerify(exactly = 0) {
            authRepository.signIn(any(), any())
        }
    }

    @Test
    fun `quando senha invalida na validacao local deve atualizar state com erro na senha`() =
        runTest {
            viewModel.signIn("teste@gmail.com", "teste")

            advanceUntilIdle()

            val state = viewModel.state.value

            assert(!state.isSuccess)
            assert(!state.isLoading)
            assert(state.userUid == null)
            assert(state.passwordError == "Senha deve ter pelo menos 6 caracteres")

            coVerify(exactly = 0) {
                authRepository.signIn(any(), any())
            }
        }

    @Test
    fun `quando senha vazia na validacao local deve atualizar state com erro na senha`() = runTest {
        viewModel.signIn("teste@gmail.com", "")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(!state.isSuccess)
        assert(!state.isLoading)
        assert(state.userUid == null)
        assert(state.passwordError == "Senha é obrigatória")

        coVerify(exactly = 0) {
            authRepository.signIn(any(), any())
        }
    }

    @Test
    fun `quando signIn falhar deve atualizar state erro`() = runTest {
        coEvery {
            authRepository.signIn(any(), any())
        } returns Result.Error("Credenciais invalidas")

        viewModel.signIn("teste@email.com", "Teste123")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(!state.isSuccess)
        assert(state.userUid == null)
        assert(!state.isLoading)
        assert(state.error == "Credenciais invalidas")

        coVerify(exactly = 1) {
            authRepository.signIn(any(), any())
        }
    }
}