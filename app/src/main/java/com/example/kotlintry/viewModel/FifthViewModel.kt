package com.example.kotlintry.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.kotlintry.repositoryAll.repository5.Repository

// 这种方法我不知道好不好使，但我觉得每次都生成一个liveData，内存不太好
class FifthViewModel:ViewModel() {

    companion object{
        const val TAG = "FifthViewModel"
    }

    private val mapLiveData = MutableLiveData<Map<String,String>>()

//    val archives = ArrayList<Archive>()

    // 这里可以获取到仓库层生成的liveData，switchMap是用来观察的，观察不同的liveData，不用这个会一直观察老的
    val listData = Transformations.switchMap(mapLiveData){query ->
        Repository.getListByDoWork(query)
    }

    fun getList(map: Map<String,String>){
        mapLiveData.value = map
    }
}