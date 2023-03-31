package com.example.kotlintry.ui.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.kotlintry.R
import com.example.kotlintry.roomTry.ArgOwner
import com.example.kotlintry.roomTry.ArgPerson
import com.example.kotlintry.ui.base.BaseFragment

class ShowDetailFragment :BaseFragment(){

    private var showText :TextView ?= null

    val args :ShowDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_show_detail,container,false)

        view.apply {
            showText = findViewById(R.id.show_text)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(val data = args.sealClass){
            is ArgOwner -> {
                Log.i(TAG, "onViewCreated: ${data.owner}")
                showText?.text = "${data.owner}"
            }
            is ArgPerson -> {
                Log.i(TAG, "onViewCreated: ${data.person}")
                showText?.text = "${data.person}"
            }
        }
    }
}