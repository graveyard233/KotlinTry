package com.example.kotlintry.roomTry

import android.util.Log
import com.example.kotlintry.viewModel.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class RoomRepository(private val videoDAO: PreciousVideoDAO,private val ownerDao: OwnerDAO) {
    val TAG: String = javaClass.simpleName



    fun getPreciousVideos()
            : Flow<DataResult<List<PreciousVideo>>> {
        return flow<DataResult<List<PreciousVideo>>> {
            RetrofitClient.retrofitClient.getPrecious().apply {
                when (code) {
                    0 -> {
                        val ownerList = mutableListOf<Owner>().apply { add(data.list[0].owner) }
                        data.list.onEach {
                            if (!ownerList.contains(it.owner)){
                                ownerList.add(it.owner)
                            } else{
                                Log.i(TAG, "getPreciousVideos: 这个owner[${it.owner.name}]已经存在")
                            }
                        }
                        ownerDao.insertAll(ownerList)
                        // 要超出5分钟的视频
                        emit(DataResult.Success(data.list.filter { it.duration > 300 }.onEach {
                            try {
                                videoDAO.insertOne(it)
                            } catch (e :Exception){
                                Log.e(TAG, "getPreciousVideos: ", e)
                            }

                        }))
                    }
                    else -> {
                        emit(DataResult.Error(message))
                    }
                }
            }
        }.catch {
            emit(DataResult.Error(it.message!!))
        }.flowOn(Dispatchers.IO)
    }

    fun selectByName(name :String): Flow<List<Owner>>  /*:Flow<DataResult<List<Owner>>>*/{
//        return flow<DataResult<List<Owner>>> {
//            ownerDao.selectByParam(name).apply {
//                when(this.isEmpty()){
//                    true ->{
//                        emit(DataResult.Error("empty"))
//                    }
//                    false ->{
//                        emit(DataResult.Success(this))
//                    }
//                }
//            }
//        }.catch {
//            emit(DataResult.Error(it.message!!))
//        }.flowOn(Dispatchers.IO)
        return ownerDao.selectByParam(name).flowOn(Dispatchers.IO).catch {
            Log.e(TAG, "selectByName: ${it.message}", it.cause)
        }
    }

    suspend fun insertOwner(owner: Owner){
        ownerDao.upsertOne(owner)
    }
}