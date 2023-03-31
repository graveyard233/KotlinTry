package com.example.kotlintry.roomTry

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.kotlintry.viewModel.DataResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RoomViewModel(private val repository : RoomRepository,private val state :SavedStateHandle) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")//由于我是和fragment的生命周期绑定，所以当fragment被navigation销毁时，这个也会被临时销毁，但还是同一个单例
    }

    private val TAG = "${javaClass.simpleName}-${javaClass.hashCode()}"
//    companion object{
//        const val TAG = "RoomViewModel"
//    }

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

    // 这里能拿到saveStateHandler的数据，这样即使app收到后台，旧了之后进程被杀掉，也能恢复，但注意不要放太多数据
    // 一般来说，除了从数据库拿数据之外，也可以靠这个来恢复数据，但界面状态就比较难恢复
    val personStateLiveData  = state.getLiveData<List<Person>>("personList")

    fun getAllPerson(){
        viewModelScope.launch {
            repository.getPersonFromRoom().collect(){
                when(it){
                    is DataResult.Success ->{
                        Log.i(TAG, "getAllPerson: success")
//                        personLiveData.postValue(it.data!!)
                        state["personList"] = it.data
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
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)){
            val savedStateHandle = extras.createSavedStateHandle()
            @Suppress("UNCHECKED_CAST")
            return RoomViewModel(repository,savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

