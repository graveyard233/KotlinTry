package com.example.kotlintry.roomTry

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PreciousVideoDAO {

    @Query("SELECT * FROM precious_video")
    fun loadAll() : Flow<List<PreciousVideo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = PreciousVideo::class)
    suspend fun insertOne(video: PreciousVideo)

    @Query("DELETE FROM precious_video")
    suspend fun deleteAll()


}


@Dao
interface OwnerDAO{

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Owner::class)
    fun insertAll(owners :List<Owner>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOne(owner: Owner)

    @Delete
    suspend fun deleteOne(owner: Owner)

    @Upsert
    suspend fun upsertOne(owner: Owner)

    @Query("SELECT * FROM owner WHERE name like '%'||:name||'%'")
    fun selectByParam(name :String) :Flow<List<Owner>>


}

