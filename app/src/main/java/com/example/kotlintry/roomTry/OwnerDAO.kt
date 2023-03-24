package com.example.kotlintry.roomTry

import androidx.room.*
import kotlinx.coroutines.flow.Flow

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