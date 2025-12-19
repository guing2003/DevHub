package com.delecrode.devhub

import android.app.Application
import com.delecrode.devhub.di.dataModule
import com.delecrode.devhub.di.repositoryModule
import com.delecrode.devhub.di.useCaseModule
import com.delecrode.devhub.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules( dataModule,
                repositoryModule,
                useCaseModule,
                viewModelModule)
        }
    }
}