package com.example.kotlintry.repositoryAll.repository6

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpClientManager {

    val mClient :OkHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        buildClient()
    }

    private fun buildClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(logging)
            followRedirects(true) // 允许失败重定向
        }.build()
    }
}