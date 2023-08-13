package com.example.network_module.network

import com.example.network_module.model.ContributorsDataItem
import com.example.network_module.model.GitHubDataResponse
import com.example.network_module.model.RepoIssuesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkRequest {
    /**
     * fetch the repositories data from github
     */
    @GET("search/repositories")
    suspend fun getRepositoriesDetails(
        @Query("q") searchName: String
    ): Response<GitHubDataResponse>

    /**
     * fetch the repositories issues from github
     * @param fullName
     */
    @GET("repos/{full_name}/issues")
    suspend fun getRepositoriesIssues(
        @Path(value = "full_name", encoded = true) fullName: String
    ): Response<List<RepoIssuesItem>>

    /**
     * fetch the repositories contributors from github
     * @param fullName
     */
    @GET("repos/{full_name}/contributors")
    suspend fun getRepositoriesContributors(
        @Path(value = "full_name", encoded = true) fullName: String
    ): Response<List<ContributorsDataItem>>
}