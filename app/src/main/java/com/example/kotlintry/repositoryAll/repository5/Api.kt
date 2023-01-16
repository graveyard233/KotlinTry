package com.example.kotlintry.repositoryAll.repository5

import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.data.BilibiliMsg
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {
    //    https://api.bilibili.com/x/polymer/space/seasons_archives_list?mid=11736402&season_id=23870&sort_reverse=false&page_num=1&page_size=30
    companion object{
        const val BASE_URL = "https://api.bilibili.com/x/" // 注意，baseUrl末尾不带斜杠
    }

    // 协程来实现
    @GET("polymer/space/seasons_archives_list")
    fun getListByCoroutine(@QueryMap map: Map<String,String>)
            : Call<BilibiliMsg<ArchiveData>> // 返回值
}