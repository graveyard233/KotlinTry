package com.example.kotlintry.repositoryAll.repository2

import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.data.BilibiliMsg

// 这个完全使用协程来实现线程切换，性能上肯定是有优势的，
// 因为可以省去了线程切换所使用的消耗，这个原项目使用了MMKV来做本地缓存
// mmkv是一个腾讯做的高性能key-value组件，可以代替sharedPreferences，但本地数据库还是用room
object DataRepository : BaseRepository() , Api {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override suspend fun getListByCoroutine(map: Map<String, String>): BilibiliMsg<ArchiveData> {
        return apiCall { service.getListByCoroutine(map) }
    }
}