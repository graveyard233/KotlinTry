package com.example.kotlintry.repositoryAll.repository1

import androidx.lifecycle.MutableLiveData
import com.example.kotlintry.data.ArchiveData

/**
 * 远程请求标准接口
 * 只为[HttpRequestManager]服务
 * */
interface IRemoteRequest {

    fun getArchiveList(map : Map<String,String> , liveData: MutableLiveData<ArchiveData>)


    suspend fun getArchiveListByCoroutine(map : Map<String,String>)
    : ArchiveData
}