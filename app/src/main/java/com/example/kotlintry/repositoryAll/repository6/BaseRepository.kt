package com.example.kotlintry.repositoryAll.repository6

import android.util.Log
import com.example.kotlintry.data.BilibiliMsg
import com.example.kotlintry.viewModel.DataResult
import java.net.ConnectException
import java.net.UnknownHostException
//import kotlin.reflect.full.isSubclassOf

open class BaseRepository {
//    /**
//     * 使用高阶函数
//     * */
//    suspend inline fun <reified T :Any> launchRequest(
//        crossinline block: suspend () -> BilibiliMsg<T>,
//        noinline onSuccess: ((T?) -> Unit)? = null,
//        noinline onError: ((String) -> Unit)? = null,
//        noinline onComplete: (() -> Unit)? = null
//    ){
//        try {
//            val response = block()
//            when(response.code){
//                0 ->{
//                    val isListType = T::class.isSubclassOf(List::class) // 这里是判断是不是list类型,但现在用不着
//                    if (response.data != null){
//                        onSuccess?.invoke(response.data)
//                    }
//                }
//                else ->{
//                    onError?.invoke(response.message)
//                }
//            }
//            onSuccess?.invoke(response.data)
//        } catch (e: Exception){
//            e.printStackTrace()
//            when (e) {
//                is UnknownHostException -> {
//                    //...
//                }
//                //...  各种需要单独处理的异常
//                is ConnectException -> {
//                    //...
//                }
//                else -> {
//                    //...
//                }
//            }
//            onError?.invoke(e.message!!)
//        } finally {
//            onComplete?.invoke()
//        }
//    }


    suspend inline fun <reified T: Any> launchRequestForResult(
        noinline block: suspend () -> BilibiliMsg<T>
    ): DataResult<T>
    {
        return try {
            val response = block()
            when(response.code){
                0 ->{
                    DataResult.Success(response.data)
                }
                else ->{
                    DataResult.Error(response.message)
                }
            }
        } catch (e: Exception){
            DataResult.Error(e.message!!)
        }

    }

    suspend inline fun <reified T: Any> launchRequestForT(
        crossinline block: suspend () -> BilibiliMsg<T>
    ): T? {
        return try {
            block()
        } catch (e: Exception){
            e.printStackTrace()
            null
        }?.run {
            when(code){
                0 ->{
                    data
                }
                else ->{
                    data// 这里可以throw出自定义exception
                }
            }
        }?: let {
            null
        }
    }
}