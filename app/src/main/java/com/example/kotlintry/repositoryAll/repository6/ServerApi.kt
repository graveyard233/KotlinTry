package com.example.kotlintry.repositoryAll.repository6

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServerApi {
    val service : CoroutineService by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        build()
    }

    private fun build(): CoroutineService{
        val retrofit = Retrofit.Builder().apply {
            baseUrl(HttpConstant.HTTP_SERVER)
            client(OkHttpClientManager.mClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
        return retrofit.create(CoroutineService::class.java)
    }
}

object HttpConstant {
    internal val HTTP_SERVER = "https://api.bilibili.com/x/"
}
