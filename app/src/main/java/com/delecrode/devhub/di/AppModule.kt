package com.delecrode.devhub.di

import com.delecrode.devhub.data.local.dataStore.AuthLocalDataSource
import com.delecrode.devhub.data.local.dataStore.AuthLocalDataSourceImpl
import com.delecrode.devhub.data.remote.firebase.UserExtraData
import com.delecrode.devhub.data.remote.webApi.instance.RetrofitInstance
import com.delecrode.devhub.data.repository.AuthRepositoryImpl
import com.delecrode.devhub.data.repository.RepoRepositoryImpl
import com.delecrode.devhub.data.repository.UserRepositoryImpl
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.RepoRepository
import com.delecrode.devhub.domain.repository.UserRepository
import com.delecrode.devhub.domain.session.SessionViewModel
import com.delecrode.devhub.ui.home.HomeViewModel
import com.delecrode.devhub.ui.login.AuthViewModel
import com.delecrode.devhub.ui.profile.ProfileViewModel
import com.delecrode.devhub.ui.register.RegisterViewModel
import com.delecrode.devhub.ui.repo.RepoDetailViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }

    single { RetrofitInstance.userApi }
    single { RetrofitInstance.repoApi }

    single<AuthLocalDataSource> { AuthLocalDataSourceImpl(get()) }

    single { com.delecrode.devhub.data.remote.firebase.FirebaseAuth(get()) }
    single { UserExtraData(get()) }

    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    single<RepoRepository> { RepoRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { RepoDetailViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { SessionViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileViewModel(get(),get()) }

}