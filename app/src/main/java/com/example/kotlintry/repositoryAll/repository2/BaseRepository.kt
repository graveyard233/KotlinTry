package com.example.kotlintry.repositoryAll.repository2

import com.example.kotlintry.data.BilibiliMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Repository数据仓库基类，主要用于协程的调用
 *
 * @author lyd  2022/1/14
 */
open class BaseRepository {

    protected suspend fun <T> apiCall(api: suspend () -> BilibiliMsg<T>)
    : BilibiliMsg<T> {
        return withContext(Dispatchers.IO) {
            api.invoke()
        }
    }
}