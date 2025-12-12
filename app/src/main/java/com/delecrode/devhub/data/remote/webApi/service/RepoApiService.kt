package com.delecrode.devhub.data.remote.webApi.service

import com.delecrode.devhub.data.model.LanguagesDto
import com.delecrode.devhub.data.model.RepoDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoApiService {

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetail(@Path("owner") owner: String, @Path("repo") repo: String): Response<RepoDetailDto>

    @GET("/repos/{owner}/{repo}/languages")
    suspend fun getRepoLanguages(@Path("owner") owner: String, @Path("repo") repo: String): Response<LanguagesDto>
}