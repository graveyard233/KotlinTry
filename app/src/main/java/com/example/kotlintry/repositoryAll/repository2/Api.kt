package com.example.kotlintry.repositoryAll.repository2

import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.data.BilibiliMsg
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {
    companion object{
        const val BASE_URL = "https://api.bilibili.com/x/" // 注意，baseUrl末尾不带斜杠
    }

    // 协程来实现
    @GET("polymer/space/seasons_archives_list")
    suspend fun getListByCoroutine(@QueryMap map: Map<String,String>)
            : BilibiliMsg<ArchiveData> // 返回值
}