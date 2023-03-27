package com.example.kotlintry.roomTry

import android.util.Log
import com.example.kotlintry.viewModel.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class RoomRepository(private val videoDAO: PreciousVideoDAO,private val ownerDao: OwnerDAO,
                     private val personDAO: PersonDAO,private val bookDAO: BookDAO) {
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

    suspend fun initTestTable(){
        personDAO.run {
            try {
                clearPerson()
                insertPerson(DataGenerator.generatePerson())// 注意
            }catch (e :Exception){
                Log.e(TAG, "init person: ", e)
            }
        }
        try {
            bookDAO.run {
                clearBook()
                upsertBook(DataGenerator.generateBook())
            }
        } catch (e: Exception) {
            Log.e(TAG, "init book: ", e)
        }
    }

    fun getPersonFromRoom()
    :Flow<DataResult<List<Person>>>
    {
        return flow<DataResult<List<Person>>> {
            emit(DataResult.Success(personDAO.getAllPerson()))
        }.catch {
            emit(DataResult.Error(it.message!!))
        }.flowOn(Dispatchers.IO)
    }

    fun getOwner()
    : Flow<DataResult<List<Owner>>>{
        return flow<DataResult<List<Owner>>> {
            // 先拉数据库的数据给用户看
            val tempList = ownerDao.getAllOwner()
            when(tempList.isNotEmpty()){
                true ->{
                    emit(DataResult.Success(tempList))
                }
                false ->{
                    emit(DataResult.Error("Empty"))
                }
            }


            // 网络请求阶段
            RetrofitClient.retrofitClient.getPrecious().apply {
                when(code){
                    0 ->{
                        val ownerList = mutableListOf<Owner>().apply { add(data.list[0].owner) }
                        data.list.onEach {
                            if (!ownerList.contains(it.owner)){
                                ownerList.add(it.owner)
                            } else{
                                Log.i(TAG, "getOwner: 这个owner[${it.owner.name}]已经存在")
                            }
                        }
                        ownerDao.insertAll(ownerList)
                        emit(DataResult.Success(ownerList))
                    }
                    else ->{
                        emit(DataResult.Error(message))
                    }
                }
            }

        }.catch {
            emit(DataResult.Error(it.message!!))
        }.flowOn(Dispatchers.IO)
    }
}