package com.delecrode.devhub.data.remote.service

import com.delecrode.devhub.data.model.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitApiService {

    @GET("users/{userName}")
    suspend fun getUser(@Path("userName") userName: String): Response<UserDto>
}