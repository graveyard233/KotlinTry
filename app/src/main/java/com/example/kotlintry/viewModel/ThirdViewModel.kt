package com.example.kotlintry.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.data.BilibiliMsg
import com.example.kotlintry.repositoryAll.repository3.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

// 这个模式直接把仓库层简化干掉了，真实项目中是不能这样干的，这个有点弱化了仓库层的作用
// 假如要加上M层，那就搞一个M层的类实现api接口，然后在仓库层里面执行协程请求，在里面做各种校验等工作
// viewModel调用那个仓库层就行
class ThirdViewModel:ViewModel() {
    companion object{
        const val TAG = "ThirdViewModel"
    }

    val listData = MutableLiveData<ArchiveData>()

    fun getListByCoroutine(map: Map<String,String>){
        loadHttp(
            request = { ApiClient.bilibiliApi.getListByCoroutine(map) },
            resp = { listData.value = it },
            err = { Log.e(TAG, "getListByCoroutine: $it") }
        )
    }

    fun getListByFlow(map: Map<String,String>,
                      ok:()->Unit,
                      error:()->Unit = {  })
    {
        launchData(
            scope = viewModelScope,
            request = { ApiClient.bilibiliApi.getListByCoroutine(map) },
            resp = {
                listData.value = it
                ok()
            }
        )
    }


    private fun <T> launchData(
        scope: CoroutineScope = GlobalScope,
        request: suspend CoroutineScope.() -> BilibiliMsg<T>,
        resp: (T?)->Unit,
        error: (String)->Unit = {  }
    ){
        scope.launch {
            flow { emit(request()) }
                .flowOn(Dispatchers.IO)
                .catch { it.message?.let { it1 -> error(it1) } }
                .collect{ resp(it.data) }
        }

    }


    private fun <T> loadHttp(
        request: suspend CoroutineScope.() -> BilibiliMsg<T>,
        resp: (T?) -> Unit,
        err: (String) -> Unit = {  },
        end: () -> Unit = {  }
    ){
        viewModelScope.launch {
            try {
                // 这里可以弹窗
                val data = request()
                if (data.code == 0){
                    resp(data.data)
                } else{
                    err(data.message)
                }
            } catch (e: Exception){
                err(e.message ?: "unknown error")
                // 这里可以弹错误提示
            } finally {
                end()
                // 这里可以结束之前的加载窗口之类的
            }
        }

    }
}

