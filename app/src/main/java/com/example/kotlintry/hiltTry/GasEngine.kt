package com.example.kotlintry.hiltTry

import javax.inject.Inject

class GasEngine @Inject constructor() :Engine {

    override fun start() {
        println("Gas engine start.")
    }

    override fun shutdown() {
        println("Gas engine shutdown.")
    }

}