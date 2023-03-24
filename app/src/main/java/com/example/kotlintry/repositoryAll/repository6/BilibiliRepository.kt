package com.example.kotlintry.repositoryAll.repository6

import com.example.kotlintry.data.ArchiveData

import com.example.kotlintry.viewModel.DataResult

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


object BilibiliRepository: BaseRepository(){

    var flow = null

//    suspend fun getList(
//        map: Map<String,String>,
//        onSuccess: ((ArchiveData?) -> Unit)? = null,
//        onError: ((String) -> Unit)? = null,
//        onComplete: (() -> Unit)? = null
//    ) {
//        launchRequest({
//            ServerApi.service.getListByCoroutine(map)
//        }, onSuccess,onError,onComplete)
////        return ServerApi.service.getListByCoroutine(map)
//    }

    suspend fun getListByResult(map: Map<String,String>)
    : DataResult<ArchiveData> {
        return launchRequestForResult {
            ServerApi.service.getListByCoroutine(map)
        }
    }

    suspend fun getListByT(map: Map<String,String>)
    : ArchiveData?{
        return launchRequestForT{
            ServerApi.service.getListByCoroutine(map)
        }
    }

    fun getListFlowResult(map: Map<String,String>):Flow<DataResult<ArchiveData>>{
        return flow<DataResult<ArchiveData>> {
            val response = ServerApi.service.getListByCoroutine(map)
            when(response.code){
                0 -> {
                    emit(DataResult.Success(response.data))
                }
                else ->{
                    emit(DataResult.Error(response.message))
                }
            }
        }.catch {
            emit(DataResult.Error(it.message!!))
        }
        .flowOn(Dispatchers.IO) // 置顶上面所有的操作都在IO线程进行
//        try {
//
//        } catch (e: Exception){
//            e.printStackTrace()
//        }
    }

}