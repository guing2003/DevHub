package com.delecrode.devhub.data.remote.webApi.instance

import android.util.Log
import com.delecrode.devhub.BuildConfig
import com.delecrode.devhub.data.remote.webApi.instance.intercptor.FullLoggingInterceptor
import com.delecrode.devhub.data.remote.webApi.service.RepoApiService
import com.delecrode.devhub.data.remote.webApi.service.UserApiService
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(FullLoggingInterceptor())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userApi: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }

    val repoApi: RepoApiService by lazy {
        retrofit.create(RepoApiService::class.java)
    }
}