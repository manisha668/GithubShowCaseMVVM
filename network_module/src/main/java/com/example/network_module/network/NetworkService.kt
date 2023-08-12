package com.example.network_module.network

import retrofit2.Response

class NetworkService(
    private val req: NetworkRequest
) {
    /**
     * fetch the repositories data from github
     */
    fun fetchRepoDetails(searchName : String) =
        this@NetworkService.getParsedApiResponse(req.getRepositoriesDetails(searchName = searchName))

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
                        msg = this@runCatching.errorBody()?.charStream()?.readText()
                    )
                }
            }
        }
}