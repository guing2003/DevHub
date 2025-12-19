package com.delecrode.devhub.di

import HomeViewModel
import com.delecrode.devhub.domain.session.SessionViewModel
import com.delecrode.devhub.presentation.ui.favoritos.RepoFavViewModel
import com.delecrode.devhub.presentation.ui.forgot.ForgotPasswordViewModel
import com.delecrode.devhub.presentation.ui.login.AuthViewModel
import com.delecrode.devhub.presentation.ui.profile.ProfileViewModel
import com.delecrode.devhub.presentation.ui.register.RegisterViewModel
import com.delecrode.devhub.presentation.ui.repo.RepoDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { HomeViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { SessionViewModel(get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { RepoDetailViewModel(get()) }
    viewModel { RepoFavViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
}
