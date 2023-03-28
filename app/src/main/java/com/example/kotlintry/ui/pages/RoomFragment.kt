package com.example.kotlintry.ui.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.kotlintry.R
import com.example.kotlintry.ui.base.BaseFragment

class RoomFragment:BaseFragment() {
    companion object{
        private const val TAG = "RoomFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_room,container,false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getView()?.apply {
            findViewById<Button>(R.id.room_btn_toOwner).setOnClickListener {
                nav().navigate(R.id.action_roomFragment_to_ownerFragment)
            }
            findViewById<Button>(R.id.room_btn_toPerson).setOnClickListener {
                nav().navigate(R.id.action_roomFragment_to_personFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}