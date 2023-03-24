package com.example.kotlintry.roomTry

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kotlintry.viewModel.DataResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RoomViewModel(private val repository : RoomRepository) : ViewModel() {

    companion object{
        const val TAG = "RoomViewModel"
    }

    fun getListFlowByState(
        ifSuccess: () -> Any? = {  },
        ifError: (errorMsg: String) -> Any? = {  }
    ){
        viewModelScope.launch {
            repository.getPreciousVideos().collect(){ dataResult ->
                when(dataResult){
                    is DataResult.Success ->{
                        dataResult.data.forEach {
                            Log.i(TAG, "getListFlowByState: aid-> ${BvUtil.av2bv("av${it.aid}")}")
                        }
//                        Log.i(TAG, "getListFlowByState: success ${dataResult.data}")

                        ifSuccess.invoke()
                    }
                    is DataResult.Error ->{
                        ifError.invoke(dataResult.error)
                    }
                }
            }
        }
    }

    fun selectOwner(name :String){
        viewModelScope.launch {
            repository.selectByName(name).collect(){
                Log.i(TAG, "size-> ${it.size} all -> $it")
            }
        }
    }

    fun insertOwner(owner: Owner){
        viewModelScope.launch {
            repository.insertOwner(owner)
        }
    }

}

class RoomViewModelFactory(private val repository: RoomRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return RoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

