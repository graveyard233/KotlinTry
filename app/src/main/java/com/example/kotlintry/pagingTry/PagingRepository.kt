package com.example.kotlintry.pagingTry

import android.util.Log
import com.example.kotlintry.repositoryAll.repository6.BaseRepository
import com.example.kotlintry.viewModel.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object PagingRepository : BaseRepository() {
    const val TAG = "PagingRepository"
    suspend fun getListFlow(map: Map<String,String>) :Flow<DataResult<VideoSearchData<BilibiliVideo>>>
    {
        return flow {
            val response = BilibiliApi.service.getSearchList(map)
            when(response.code){
                0 ->{
                    emit(DataResult.Success(response.data))
                }
                else ->{
                    emit(DataResult.Error(response.message))
                }
            }
        }.catch {
            emit(DataResult.Error(it.message!!))
        }.flowOn(Dispatchers.IO)
    }

    fun getCookie(){

        MyOkHttpClient.mClient
            .newCall(Request.Builder().url("https://bilibili.com").build())
            .enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "onFailure: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    val msg = response.body?.string()
                    val mList = ArrayList<String>()
                    response.headers.values("set-cookie")
                        .filter { it.contains("path=") }
                        .forEach {
                            mList.add(StringBuilder(it).replace("path=/;".toRegex(),""))
                        }
                    Log.i(TAG, "onResponse: $mList")
                }

            })
    }

    suspend fun getRetrofitCookie():Flow<String>{// 这里先获取一次cookie
        return flow {
            val body = BilibiliApi.cookieService.getCookie("https://bilibili.com/")
            emit("ok")
        }.catch {
            Log.e(TAG, "getRetrofitCookie: ${it.message}")
        }.flowOn(Dispatchers.IO)
    }

}