package com.delecrode.devhub.di

import com.delecrode.devhub.domain.useCase.AuthUseCase
import com.delecrode.devhub.domain.useCase.FetchUserDataUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { FetchUserDataUseCase(get()) }
    factory { AuthUseCase(get()) }
}
