package com.example.kotlintry.repositoryAll.repository4

import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.data.BilibiliMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 仓库层
object RemoteRepository {

    private val service by lazy { RequestManager.getService(Api::class.java) }

    suspend fun getListByCoroutine(map: Map<String,String>) : BilibiliMsg<ArchiveData>
    {
        return withContext(Dispatchers.IO){
            service.getListByCoroutine(map)
        }
    }

}