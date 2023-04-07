package com.example.kotlintry.hiltTry

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlintry.roomTry.PersonDAO
import com.example.kotlintry.roomTry.VideoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

/**
 * room和hilt的简单结合使用
 * */
@Module
@InstallIn(SingletonComponent::class)
object RoomModel {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) :VideoDatabase{
        return Room.databaseBuilder(application,VideoDatabase::class.java,"bilibili_database")
            .fallbackToDestructiveMigration()
//            .addCallback(object : RoomDatabase.Callback(){
//                override fun onCreate(db: SupportSQLiteDatabase) {
//                    super.onCreate(db)
//                    CoroutineScope(Dispatchers.IO).launch {
//                        // 我拿不到DAO 做不了初始化，尴尬
//                    }
//                }
//            })
            .build()
    }

    @Provides
    @Singleton
    fun providePersonDao(videoDatabase: VideoDatabase):PersonDAO{
        return videoDatabase.personDao()
    }


}