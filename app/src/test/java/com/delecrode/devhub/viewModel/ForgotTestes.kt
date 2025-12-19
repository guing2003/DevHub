package com.delecrode.devhub.viewModel

import com.delecrode.devhub.MainDispatcherRule
import com.delecrode.devhub.data.utils.Result
import com.delecrode.devhub.domain.model.UserAuth
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.presentation.ui.forgot.ForgotPasswordViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ForgotTestes {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val authRepository: AuthRepository = mockk()
    private lateinit var viewModel: ForgotPasswordViewModel

    @Before
    fun setup() {
        viewModel = ForgotPasswordViewModel(authRepository)
    }

    @Test
    fun `quando forgotPassword sucesso deve atualizar state com sucesso`() = runTest {

        coEvery {
            authRepository.forgotPassword("teste@email.com")
        } returns Result.Success(Unit)

        viewModel.forgotPassword("teste@email.com")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(state.isSuccess)
        assert(!state.isLoading)
        assert(state.error == null)

        coVerify(exactly = 1) {
            authRepository.forgotPassword(any())
        }
    }

    @Test
    fun `quando forgotPassword falhar deve atualizar state erro`() = runTest{
        coEvery {
            authRepository.forgotPassword("teste@email.com")
        } returns Result.Error("Erro ao enviar e-mail")

        viewModel.forgotPassword("teste@email.com")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(!state.isSuccess)
        assert(!state.isLoading)
        assert(state.error == "Erro ao enviar e-mail")

        coVerify(exactly = 1) {
            authRepository.forgotPassword(any())
        }
    }

    @Test
    fun `quando email invalido na validacao local deve atualizar state com erro no email`() = runTest{
        viewModel.forgotPassword("teste")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(!state.isSuccess)
        assert(!state.isLoading)
        assert(state.error == null)
        assert(state.emailError == "Email inválido")

        coVerify(exactly = 0) {
            authRepository.forgotPassword(any())
        }
    }

    @Test
    fun `quando email vazio na validacao local deve atualizar state com erro no email`() = runTest{
        viewModel.forgotPassword("")

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(!state.isSuccess)
        assert(!state.isLoading)
        assert(state.error == null)
        assert(state.emailError == "Email é obrigatório")

        coVerify(exactly = 0) {
            authRepository.forgotPassword(any())
        }

    }
}