package com.example.kotlintry.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.repositoryAll.repository1.HttpRequestManager
import kotlinx.coroutines.launch

class FirstViewModel : ViewModel() {

    companion object{
        @JvmField
        val requestMap = mapOf("mid" to "11736402","season_id" to "23870",
        "sort_reverse" to "false","page_num" to "1","page_size" to "30")
    }

    val listData = MutableLiveData<ArchiveData>()

    fun getList(map: Map<String,String>){
        HttpRequestManager.instance.getArchiveList(map,listData)
    }

    fun getListByCoroutine(map: Map<String,String>){
        // 这里做数据校验

        // GlobalScope(Dispatchers.Main) 全局作用域 默认是异步线程
        // viewModelScope.launch 默认是主线程 == (Dispatchers.Main)
        viewModelScope.launch {
            // 这里是主线程，想干啥干啥，比如弹窗

            val result = HttpRequestManager.instance.getArchiveListByCoroutine(map)

            if (result != null){
                listData?.value = result
            } else{
                Log.i("TAG", "getListByCoroutine: error")
            }
            // 结束之前的弹窗
        }
    }

}