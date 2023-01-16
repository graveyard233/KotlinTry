package com.example.kotlintry.repositoryAll.repository5

import androidx.lifecycle.liveData
import com.example.kotlintry.data.ArchiveData
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {

    // 在这里是响应式编程，这里我们可以返回一个liveData，提供一个挂起函数 切线程，最后emit发射出去
    fun getListByLiveData(map: Map<String,String>) = liveData(Dispatchers.IO){
        val result = try {
            val response = BilibiliNetWork.getList(map)
            if (response.code == 0){
                val archiveData = response.data
                Result.success(archiveData)
            } else{
                Result.failure(RuntimeException("error is ${response.message}"))
            }
        } catch (e :Exception){
            Result.failure<ArchiveData>(e)
        }
        emit(result)
    }

    fun getListByDoWork(map: Map<String,String>) = doWork(Dispatchers.IO){
        val response = BilibiliNetWork.getList(map)
        if (response.code == 0){
            val archiveData = response.data
            Result.success(archiveData)
        } else{
            Result.failure(RuntimeException("error is ${response.message}"))
        }
    }

    private fun <T> doWork(context: CoroutineContext,
                           block: suspend () -> Result<T>) =
        liveData<Result<T>>(context){
            val result = try {
                block()
            } catch (e :Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }
}