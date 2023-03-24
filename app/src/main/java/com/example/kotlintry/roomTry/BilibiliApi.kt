package com.example.kotlintry.roomTry

import com.example.kotlintry.data.BilibiliMsg
import retrofit2.http.GET


interface BilibiliApi {
    companion object{
        const val BASE_URL = "https://api.bilibili.com/x/"
    }

    @GET("web-interface/popular/precious?page_size=20&page=1")
    suspend fun getPrecious()
    : BilibiliMsg<PreciousVideoData>
}