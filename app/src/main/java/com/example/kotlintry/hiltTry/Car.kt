package com.example.kotlintry.hiltTry

import javax.inject.Inject

class Car @Inject constructor(private val driver :Driver) {

    @BindElectricEngine // 用于解决相同类型注入不同实例的问题
    @Inject
    lateinit var engine: Engine

    fun deliver(){
        engine.start()
        println("Truck is delivering cargo.Driver is $driver")
        engine.shutdown()
    }

}