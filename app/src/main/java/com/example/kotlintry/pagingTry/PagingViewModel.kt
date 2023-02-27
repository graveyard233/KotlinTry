package com.example.kotlintry.pagingTry

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kotlintry.viewModel.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PagingViewModel : ViewModel(){
    companion object{
        const val TAG = "PagingViewModel"
    }

    private val repository = PagingRepository

    val listData :MutableLiveData<List<BilibiliVideo>> = MutableLiveData()


    fun getListFlow(map: MutableMap<String,String>){
        viewModelScope.launch {
            repository.getListFlow(map).collect(){
                when(it){
                    is DataResult.Success ->{
                        Log.i(TAG, "getListFlow: ${it.data.result}")
                    }
                    is DataResult.Error ->{
                        Log.e(TAG, "getListFlow: ${it.error}")
                    }
                }
            }
        }
    }

    fun getCookie(){
        viewModelScope.launch{
            repository.getRetrofitCookie().collect{
                Log.i(TAG, "getCookie: $it")
            }
        }
    }

    fun getPagingData():Flow<PagingData<BilibiliVideo>>{
        return PagingRepository.getPagingData().cachedIn(viewModelScope)
    }

}