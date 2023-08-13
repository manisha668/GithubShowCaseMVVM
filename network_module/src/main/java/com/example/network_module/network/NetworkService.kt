package com.example.network_module.network

import retrofit2.Response

class NetworkService(
    private val req: NetworkRequest
) {
    /**
     * fetch the repositories data from github
     */
    suspend fun fetchRepoDetails(searchName : String) =
        this@NetworkService.getParsedApiResponse(req.getRepositoriesDetails(searchName = searchName))

    /**
     * fetch the repositories issues from github for a selected repo
     */
    suspend fun fetchRepositoriesIssues(fullName : String) =
        this@NetworkService.getParsedApiResponse(req.getRepositoriesIssues(fullName = fullName))

    /**
     * fetch the repositories contributors from github for a selected repo
     */
    suspend fun fetchRepositoriesContributors(fullName : String) =
        this@NetworkService.getParsedApiResponse(req.getRepositoriesContributors(fullName = fullName))

    /**
     * The method helps in wrapping the api response with [Response] and directly returns the data if
     * successful else error message will be returned
     */
    private fun <T> getParsedApiResponse(networkCallResult: Response<T>) =
        networkCallResult.runCatching {
            when {
                this@runCatching.isSuccessful -> NetworkCallStatus.Success(data = this@runCatching.body())
                else -> {
                    NetworkCallStatus.Error(
                        msg = this@runCatching.errorBody()?.charStream()?.toString()
                    )
                }
            }
        }.getOrThrow()
}