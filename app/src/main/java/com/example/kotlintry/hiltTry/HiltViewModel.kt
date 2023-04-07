package com.example.kotlintry.hiltTry

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

// hilt和viewModel结合使用，感觉没啥区别
@dagger.hilt.android.lifecycle.HiltViewModel
class HiltViewModel @Inject constructor(private val repository: HiltRepository, private val state :SavedStateHandle) :ViewModel() {

    val TAG = javaClass.simpleName

    init {
        Log.i(TAG, "init")
    }
}