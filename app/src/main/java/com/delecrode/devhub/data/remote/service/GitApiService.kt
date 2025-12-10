package com.delecrode.devhub.data.remote.service

import com.delecrode.devhub.data.model.RepoDetailDto
import com.delecrode.devhub.data.model.ReposDto
import com.delecrode.devhub.data.model.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitApiService {

    @GET("users/{userName}")
    suspend fun getUser(@Path("userName") userName: String): Response<UserDto>

    @GET("users/{userName}/repos")
    suspend fun getRepos(@Path("userName") userName: String) : Response<List<ReposDto>>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetail(@Path("owner") owner: String, @Path("repo") repo: String): Response<RepoDetailDto>
}