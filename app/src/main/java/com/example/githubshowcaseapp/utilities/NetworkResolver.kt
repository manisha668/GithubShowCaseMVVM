package com.example.githubshowcaseapp.utilities

import com.example.network_module.network.NetworkRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkResolver {

    private const val BASE_URL = "https://api.github.com"

    private val okHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    val retrofitInstance by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NetworkRequest::class.java)
    }
}