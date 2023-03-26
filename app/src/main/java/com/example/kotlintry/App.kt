package com.example.kotlintry

import android.app.Application
import com.example.kotlintry.roomTry.RoomRepository
import com.example.kotlintry.roomTry.VideoDatabase
import com.tencent.mmkv.MMKV

class App:Application() {

    val videoDB by lazy {
        VideoDatabase.getDatabase(this)
    }

    val roomRepository by lazy {
        RoomRepository(videoDB.videoDao(),videoDB.ownerDao(),videoDB.personDao(),videoDB.bookDao())
    }

    override fun onCreate() {
        super.onCreate()

        val rootDir = MMKV.initialize(this)
        println("mmkv root: $rootDir")
    }
}