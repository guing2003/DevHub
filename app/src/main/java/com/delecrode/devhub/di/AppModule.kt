package com.delecrode.devhub.di

import androidx.room.Room
import com.delecrode.devhub.data.local.dataStore.AuthLocalDataSource
import com.delecrode.devhub.data.local.dataStore.AuthLocalDataSourceImpl
import com.delecrode.devhub.data.local.database.AppDatabase
import com.delecrode.devhub.data.local.database.data.RepoLocalDataSource
import com.delecrode.devhub.data.local.database.data.RepoLocalDataSourceImpl
import com.delecrode.devhub.data.remote.firebase.UserExtraData
import com.delecrode.devhub.data.remote.webApi.instance.RetrofitInstance
import com.delecrode.devhub.data.repository.AuthRepositoryImpl
import com.delecrode.devhub.data.repository.RepoRepositoryImpl
import com.delecrode.devhub.data.repository.UserRepositoryImpl
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.RepoRepository
import com.delecrode.devhub.domain.repository.UserRepository
import com.delecrode.devhub.domain.session.SessionViewModel
import com.delecrode.devhub.ui.favoritos.RepoFavViewModel
import com.delecrode.devhub.ui.forgot.ForgotPasswordViewModel
import com.delecrode.devhub.ui.home.HomeViewModel
import com.delecrode.devhub.ui.login.AuthViewModel
import com.delecrode.devhub.ui.profile.ProfileViewModel
import com.delecrode.devhub.ui.register.RegisterViewModel
import com.delecrode.devhub.ui.repo.RepoDetailViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }

    single { RetrofitInstance.userApi }
    single { RetrofitInstance.repoApi }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    single {
        get<AppDatabase>().repoDao()
    }

    single<AuthLocalDataSource> { AuthLocalDataSourceImpl(get()) }
    single<RepoLocalDataSource> { RepoLocalDataSourceImpl(get()) }

    single { com.delecrode.devhub.data.remote.firebase.FirebaseAuth(get()) }
    single { UserExtraData(get()) }

    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    single<RepoRepository> { RepoRepositoryImpl(get(), get(), get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { RepoDetailViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { SessionViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { RepoFavViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get()) }
}