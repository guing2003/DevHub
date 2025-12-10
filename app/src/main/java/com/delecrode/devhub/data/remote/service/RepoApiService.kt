package com.delecrode.devhub.data.remote.service

import com.delecrode.devhub.data.model.RepoDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoApiService {

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetail(@Path("owner") owner: String, @Path("repo") repo: String): Response<RepoDetailDto>
}