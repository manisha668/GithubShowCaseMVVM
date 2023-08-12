package com.example.network_module.network

import com.example.network_module.model.GitHubDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkRequest {
    /**
     * fetch the repositories data from github
     */
    @GET("search/repositories")
    fun getRepositoriesDetails(
        @Query("q") searchName: String
    ): Response<GitHubDataResponse>
}