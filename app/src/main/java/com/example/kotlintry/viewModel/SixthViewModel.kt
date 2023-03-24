package com.example.kotlintry.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.repositoryAll.repository6.BaseRepository
import com.example.kotlintry.repositoryAll.repository6.BilibiliRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion

// https://juejin.cn/post/6962921891501703175
class SixthViewModel() : ViewModel() {
    companion object{
        const val TAG = "SixthViewModel"
    }
    private val repository =  BilibiliRepository

    private val _List: MutableLiveData<ArchiveData> = MutableLiveData()
    val listData : LiveData<ArchiveData> = _List

    val _test: MutableLiveData<DataResult<String>> = MutableLiveData()

//    fun getList(map : Map<String,String>){
//        requestMain {
//            repository.getList(map,{
//                _List.postValue(it)
//            },{
//                Log.e(TAG, "getList: $it")
//            },{
//                // 结束时要干的活
//            })
////            _List.postValue(result.data!!)
//        }
//    }

    /** 多状态函数返回值*/
    fun getListByResult(map : Map<String,String>){
        requestMain {
            val result = repository.getListByResult(map)
            when(result){
                is DataResult.Success/*<ArchiveData>*/ ->{
                    _List.postValue(result.data!!)
                }
                is DataResult.Error ->{
                    Log.e(TAG, "getListByResult: ${result.error}")
                }
            }
        }
    }

    fun getListByT(map : Map<String,String>){
        requestMain {
            val archiveData = try {
                repository.getListByT(map)
            } catch (e: Exception){
                e.printStackTrace()
            } as ArchiveData
            _List.postValue(archiveData)
        }
    }

    fun getListFlow(map: Map<String, String>){
        viewModelScope.launch {
            repository.getListFlowResult(map).collect(){
                when(it){
                    is DataResult.Error ->{
                        Log.e(TAG, "getListFlow: $it")
                    }
                    is DataResult.Success ->{
                        _List.postValue(it.data!!)
                    }
                }
            }
        }
    }

    fun getListFlowByState(
        map: Map<String, String>,
        ifSuccess: () -> Any? = {  },
        ifError: (errorMsg: String) -> Any? = {  }
    ){
        viewModelScope.launch {
            repository.getListFlowResult(map).onCompletion {
                Log.i(TAG, "getListFlowByState: 完成了 $it")
            }.collect(){
                when(it){
                    is DataResult.Success ->{
                        _List.postValue(it.data!!)
                        ifSuccess.invoke()
                    }
                    is DataResult.Error ->{
                        ifError.invoke(it.error)
                    }
                }
            }
        }
    }
}

inline fun SixthViewModel.requestMain(
    noinline block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch() {
        block.invoke(this)
    }
}

inline fun SixthViewModel.requestIO(
    noinline block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        block.invoke(this)
    }
}

inline fun SixthViewModel.delayMain(
    delayTime: Long,
    noinline block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch() {
        withContext(Dispatchers.IO) {
            delay(delayTime)
        }
        block.invoke(this)
    }
}