package com.example.kotlintry.roomTry

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.*

@Database(entities = [PreciousVideo::class,Owner::class,Person::class,Book::class], version = 1, exportSchema = false)
public abstract class VideoDatabase :RoomDatabase(){
    abstract fun videoDao() : PreciousVideoDAO

    abstract fun ownerDao() : OwnerDAO

    abstract fun personDao() : PersonDAO

    abstract fun bookDao() : BookDAO

    companion object{

        @Volatile
        private var INSTANCE : VideoDatabase ?= null

        fun getDatabase(context: Context): VideoDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VideoDatabase::class.java,
                    "bilibili_database"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // 在这里进行初始化工作
                        INSTANCE?.run {
                            CoroutineScope(Dispatchers.IO).launch {
                                personDao().clearPerson()
                                personDao().insertPerson(DataGenerator.generatePerson())
                                delay(100)
                                Log.i("TAG", "onCreate: 初始化dataBase")
                                bookDao().clearBook()
                                bookDao().upsertBook(DataGenerator.generateBook())
                            }


                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }


    }

}