package com.example.kotlintry.roomTry

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PreciousVideo::class,Owner::class], version = 1, exportSchema = false)
public abstract class VideoDatabase :RoomDatabase(){
    abstract fun videoDao() : PreciousVideoDAO

    abstract fun ownerDao() : OwnerDAO

    companion object{

        @Volatile
        private var INSTANCE : VideoDatabase ?= null

        fun getDatabase(context: Context): VideoDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VideoDatabase::class.java,
                    "bilibili_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}