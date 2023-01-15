package com.example.kotlintry.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.repositoryAll.repository2.DataRepository
import com.tencent.mmkv.MMKV

class SecondViewModel : BaseViewModel() {
    override fun start() {  }

    val listData = MutableLiveData<ArchiveData>()

    // 尝试mmkv
    private val mmkv by lazy { MMKV.defaultMMKV() }

    fun getList(map : Map<String,String>,
                       successCall : () ->Any? = {}){
        launch({
            handleRequest(
                DataRepository.getListByCoroutine(map),
            successBlock = {
                // 这里可以做一些数据的存储记录，然后在设值
                listData.value = it.data!!
                // 鬼鬼，这读写也太快了，基本一毫秒就写好了，一共两个文件一个crc是放校验码，还有序列号，剩下一个放你的数据
                mmkv.encode("archives",it.data.archives.toString())
                successCall.invoke()
            })
        })


    }
}