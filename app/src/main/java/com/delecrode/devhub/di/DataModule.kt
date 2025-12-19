package com.delecrode.devhub.di

import androidx.room.Room
import com.delecrode.devhub.data.local.dataStore.AuthLocalDataSource
import com.delecrode.devhub.data.local.dataStore.AuthLocalDataSourceImpl
import com.delecrode.devhub.data.local.database.AppDatabase
import com.delecrode.devhub.data.local.database.MIGRATION_1_2
import com.delecrode.devhub.data.local.database.data.RepoLocalDataSource
import com.delecrode.devhub.data.local.database.data.RepoLocalDataSourceImpl
import com.delecrode.devhub.data.remote.firebase.UserExtraData
import com.delecrode.devhub.data.remote.webApi.instance.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }

    single { RetrofitInstance.userApi }
    single { RetrofitInstance.repoApi }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    single { get<AppDatabase>().repoDao() }

    single<AuthLocalDataSource> { AuthLocalDataSourceImpl(get()) }
    single<RepoLocalDataSource> { RepoLocalDataSourceImpl(get()) }

    single { com.delecrode.devhub.data.remote.firebase.FirebaseAuth(get()) }
    single { UserExtraData(get()) }
}
