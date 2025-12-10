package com.delecrode.devhub.di

import com.delecrode.devhub.data.remote.RetrofitInstance
import com.delecrode.devhub.data.repository.GitRepositoryImpl
import com.delecrode.devhub.domain.repository.GitRepository
import com.delecrode.devhub.ui.home.HomeViewModel
import com.delecrode.devhub.ui.repo.RepoDetailViewModel
import org.koin.dsl.module

val appModule = module{

    single { RetrofitInstance.gitApi }
    single<GitRepository> { GitRepositoryImpl(get()) }

    single { HomeViewModel(get())}
    single{ RepoDetailViewModel(get()) }

}