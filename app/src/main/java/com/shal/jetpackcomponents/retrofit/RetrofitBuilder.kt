package com.shal.jetpackcomponents.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val baseUrl = "https://jsonplaceholder.typicode.com/todos/"

    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttp = OkHttpClient.Builder().addInterceptor(logger)

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
        .build()

    fun <T> getRetrofitBilder(service: Class<T>): T {
        return retrofit.create(service)

    }
}