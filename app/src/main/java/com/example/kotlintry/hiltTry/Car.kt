package com.example.kotlintry.hiltTry

import javax.inject.Inject

class Car @Inject constructor(private val driver :Driver) {

//    @Inject
//    lateinit var engine: Engine

    fun deliver(){
//        engine.start()
        println("Truck is delivering cargo.Driver is $driver")
//        engine.shutdown()
    }

}