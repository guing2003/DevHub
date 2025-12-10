package com.delecrode.devhub.data.remote.service

import com.delecrode.devhub.data.model.ReposDto
import com.delecrode.devhub.data.model.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {

    @GET("users/{userName}")
    suspend fun getUser(@Path("userName") userName: String): Response<UserDto>

    @GET("users/{userName}/repos")
    suspend fun getReposForUser(@Path("userName") userName: String) : Response<List<ReposDto>>
}