package com.example.kotlintry.repositoryAll.repository3

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    @JvmStatic
    val okClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @JvmStatic
    val retrofitClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BilibiliApi.BASE_URL)
            .client(okClient)
            .build()
    }
}