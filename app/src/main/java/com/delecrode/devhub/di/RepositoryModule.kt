package com.delecrode.devhub.di

import com.delecrode.devhub.data.repository.AuthRepositoryImpl
import com.delecrode.devhub.data.repository.RepoRepositoryImpl
import com.delecrode.devhub.data.repository.UserRepositoryImpl
import com.delecrode.devhub.domain.repository.AuthRepository
import com.delecrode.devhub.domain.repository.RepoRepository
import com.delecrode.devhub.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<UserRepository> {
        UserRepositoryImpl(get(), get(), get())
    }

    single<RepoRepository> {
        RepoRepositoryImpl(get(), get(), get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get(), get(), get())
    }
}
