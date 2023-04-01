package com.example.kotlintry.ui.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.kotlintry.R
import com.example.kotlintry.hiltTry.Car
import com.example.kotlintry.hiltTry.HiltViewModel
import com.example.kotlintry.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class HiltFragment: BaseFragment() {


    private lateinit var textHilt :TextView

    private val hiltViewModel :HiltViewModel by viewModels<HiltViewModel>()

    // 注意，注入的类不能为private，但类里面可以有private
    @Inject
    lateinit var car :Car

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var retrofit: Retrofit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_hilt,container,false)

        view.apply {
            textHilt = findViewById(R.id.hilt_text)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        car.deliver()

    }
}