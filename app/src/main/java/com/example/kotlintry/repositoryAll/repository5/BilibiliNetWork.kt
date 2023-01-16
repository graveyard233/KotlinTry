package com.example.kotlintry.repositoryAll.repository5

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


object BilibiliNetWork {

    private val service by lazy { ServiceCreator.getService(Api::class.java) }

    suspend fun getList(map: Map<String,String>) = service.getListByCoroutine(map).await()


    // 这一套的做法是回归原始方案，在把方法声明为协程（挂起）函数，
    // await方法会先阻塞当前线程，获取到请求后，解析出来的数据模型对象取出并返回到上一层
    private suspend fun <T> Call<T>.await() : T{
        return suspendCoroutine { continuation ->
            enqueue(object :Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null){
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })

        }
    }
}