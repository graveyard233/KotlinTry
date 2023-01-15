package com.example.kotlintry.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.data.BilibiliMsg
import com.example.kotlintry.repositoryAll.repository4.RemoteRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FourthViewModel : ViewModel(){

    companion object{
        const val TAG = "FourthViewModel"
    }

    val listData = MutableLiveData<ArchiveData>()

    // 这样子不好，因为你不知道请求（上游）哪里出了问题，应该按返回的错误码或者信息来分类处理
    fun getList(map: Map<String,String>){
        viewModelScope.rxLaunch<BilibiliMsg<ArchiveData>> {
            onRequest = { // 网络请求
                RemoteRepository.getListByCoroutine(map)
            }
            onSuccess = { // 成功回调
                listData.value = it.data!!
            }
            onError = { // 失败回调
                Log.e(TAG, "getList: ", it)
            }
        }
    }

    private fun <T> CoroutineScope.rxLaunch(init: CoroutineBuilder<T>.() ->Unit){
        val result = CoroutineBuilder<T>().apply(init)
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            result.onError?.invoke(exception)
        }
        launch(coroutineExceptionHandler){
            val res: T? = result.onRequest?.invoke()
            res?.let { result.onSuccess?.invoke(it) }
        }
    }

    private class CoroutineBuilder<T>{
        var onRequest: (suspend () -> T)? = null
        var onSuccess: ((T) -> Unit)? = null
        var onError: ((Throwable) -> Unit)? = null
    }
}