package com.example.kotlintry.repositoryAll.repository6

import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.data.BilibiliMsg
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CoroutineService {
    // 协程来实现
    @GET("polymer/space/seasons_archives_list")
    suspend fun getListByCoroutine(@QueryMap map: Map<String,String>)
            : BilibiliMsg<ArchiveData> // 返回值
}