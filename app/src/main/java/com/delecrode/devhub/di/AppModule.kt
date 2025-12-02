package com.delecrode.devhub.di

import com.delecrode.devhub.data.remote.RetrofitInstance
import com.delecrode.devhub.data.repository.GitRepositoryImpl
import com.delecrode.devhub.domain.repository.GitRepository
import com.delecrode.devhub.ui.profile.ProfileViewModel
import org.koin.dsl.module

val appModule = module{

    single { RetrofitInstance.gitApi }
    single<GitRepository> { GitRepositoryImpl(get()) }

    single { ProfileViewModel(get())}

}