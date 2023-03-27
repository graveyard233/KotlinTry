package com.example.kotlintry.roomTry

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kotlintry.viewModel.DataResult
import kotlinx.coroutines.flow.MutableSharedFlow
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

    fun initTestTable(){
        viewModelScope.launch {
            repository.initTestTable()
        }
    }


    val personLiveData :MutableLiveData<List<Person>> = MutableLiveData()
    fun getAllPerson(){
        viewModelScope.launch {
            repository.getPersonFromRoom().collect(){
                when(it){
                    is DataResult.Success ->{
                        personLiveData.postValue(it.data!!)
                    }
                    is DataResult.Error ->{
                        Log.e(TAG, "getAllPerson: ${it.error}")
                    }
                }
            }
        }
    }


    val ownerSharedFlow :MutableSharedFlow<List<Owner>> = MutableSharedFlow(replay = 1/*, extraBufferCapacity = 1*/)
    var count = 1

    fun getAllOwner(){
        viewModelScope.launch {
            repository.getOwner().collect(){
                Log.i(TAG, "getAllOwner: 接收到 ${count++} 次")
                when(it){
                    is DataResult.Success ->{
                        Log.i(TAG, "getAllOwner: 有数据->${it.data}")
                        ownerSharedFlow.emit(it.data)
                    }
                    is DataResult.Error ->{
                        Log.e(TAG, "getAllOwner: ${it.error}")
                    }
                }
            }
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

