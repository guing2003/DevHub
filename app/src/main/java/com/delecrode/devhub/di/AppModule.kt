package com.delecrode.devhub.di

import com.delecrode.devhub.data.remote.RetrofitInstance
import com.delecrode.devhub.data.repository.RepoRepositoryImpl
import com.delecrode.devhub.data.repository.UserRepositoryImpl
import com.delecrode.devhub.domain.repository.RepoRepository
import com.delecrode.devhub.domain.repository.UserRepository
import com.delecrode.devhub.ui.home.HomeViewModel
import com.delecrode.devhub.ui.repo.RepoDetailViewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitInstance.userApi }
    single { RetrofitInstance.repoApi }

    single<UserRepository> { UserRepositoryImpl(get()) }
    single<RepoRepository> { RepoRepositoryImpl(get()) }

    single { HomeViewModel(get()) }
    single { RepoDetailViewModel(get()) }

}