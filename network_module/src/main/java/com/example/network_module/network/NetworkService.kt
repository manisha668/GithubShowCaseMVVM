package com.example.network_module.network

import retrofit2.Response

class NetworkService(
    private val req: NetworkRequest
) {
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