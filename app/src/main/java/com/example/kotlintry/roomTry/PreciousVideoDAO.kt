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


