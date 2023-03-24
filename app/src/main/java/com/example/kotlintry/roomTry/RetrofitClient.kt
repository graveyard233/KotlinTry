package com.example.kotlintry.roomTry

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    @JvmStatic
    val okClient : OkHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder().apply {
//            addInterceptor(logging)
            followRedirects(true) // 允许失败重定向
        }.build()
    }

    @JvmStatic
    val retrofitClient : BilibiliApi by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        Retrofit.Builder().apply {
            baseUrl(BilibiliApi.BASE_URL)
            client(okClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(BilibiliApi::class.java)
    }
}