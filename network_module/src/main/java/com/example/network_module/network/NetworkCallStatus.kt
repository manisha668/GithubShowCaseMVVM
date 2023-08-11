package com.example.network_module.network

sealed class NetworkCallStatus<out T>(
    val data: T? = null,
    val msg: String? = null
) {
    class Loading<T>(data: T? = null) : NetworkCallStatus<T>()
    class Success<T>(data: T?) : NetworkCallStatus<T>(data)
    class Error<T>(data: T? = null, msg: String?) : NetworkCallStatus<T>(data, msg)
}