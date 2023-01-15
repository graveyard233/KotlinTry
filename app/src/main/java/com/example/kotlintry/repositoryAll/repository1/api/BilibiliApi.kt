package com.example.kotlintry.repositoryAll.repository1.api

import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.data.BilibiliMsg
import com.example.kotlintry.data.ResponseWrapper

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BilibiliApi {
    //    https://api.bilibili.com/x/polymer/space/seasons_archives_list?mid=11736402&season_id=23870&sort_reverse=false&page_num=1&page_size=30
    companion object{
        const val BASE_URL = "https://api.bilibili.com/x/" // 注意，baseUrl末尾不带斜杠
    }

    /* @FormUrlEncoded 这玩意只能用在post请求中*/
    @GET("polymer/space/seasons_archives_list")
    fun getArchivesList(@QueryMap map: Map<String,String>)
            : Observable<BilibiliMsg<ArchiveData>> // 这玩意是返回值

    // 协程来实现
    @GET("polymer/space/seasons_archives_list")
    suspend fun getListByCoroutine(@QueryMap map: Map<String,String>)
            : BilibiliMsg<ArchiveData> // 返回值

}