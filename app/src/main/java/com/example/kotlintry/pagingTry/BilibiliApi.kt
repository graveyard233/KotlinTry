package com.example.kotlintry.pagingTry

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object BilibiliApi {
    val service :BilibiliService by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        build()
    }

    private fun build(): BilibiliService{
        val retrofit = Retrofit.Builder().apply {
            baseUrl(PagingHttpConstant.HTTP_SERVER)
            client(MyOkHttpClient.mClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
        return retrofit.create(BilibiliService::class.java)
    }

    val cookieService :BilibiliService by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        Retrofit.Builder().apply {
            baseUrl(PagingHttpConstant.HTTP_SERVER)
            client(MyOkHttpClient.cookieClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(BilibiliService::class.java)
    }
}

object PagingHttpConstant {
    internal val HTTP_SERVER = "https://api.bilibili.com/x/"
}