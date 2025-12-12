package com.delecrode.devhub.di

import com.delecrode.devhub.data.firebase.UserExtraData
import com.delecrode.devhub.data.remote.RetrofitInstance
import com.delecrode.devhub.data.repository.AuthRepositoryImpl
import com.delecrode.devhub.data.repository.RepoRepositoryImpl
import com.delecrode.devhub.data.repository.UserRepositoryImpl
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.RepoRepository
import com.delecrode.devhub.domain.repository.UserRepository
import com.delecrode.devhub.domain.session.SessionViewModel
import com.delecrode.devhub.ui.home.HomeViewModel
import com.delecrode.devhub.ui.login.AuthViewModel
import com.delecrode.devhub.ui.register.RegisterViewModel
import com.delecrode.devhub.ui.repo.RepoDetailViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val appModule = module {

    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }

    single { RetrofitInstance.userApi }
    single { RetrofitInstance.repoApi }



    // Seus data sources customizados
    single { com.delecrode.devhub.data.firebase.FirebaseAuth(get()) }
    single { UserExtraData(get()) }

    single<UserRepository> { UserRepositoryImpl(get()) }
    single<RepoRepository> { RepoRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    viewModel{ HomeViewModel(get()) }
    viewModel { RepoDetailViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { SessionViewModel(get()) }
    viewModel { RegisterViewModel(get()) }

}