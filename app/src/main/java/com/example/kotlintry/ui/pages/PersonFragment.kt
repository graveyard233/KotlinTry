package com.example.kotlintry.ui.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintry.App
import com.example.kotlintry.R
import com.example.kotlintry.roomTry.RoomPersonAdapter
import com.example.kotlintry.roomTry.RoomViewModel
import com.example.kotlintry.roomTry.RoomViewModelFactory
import com.example.kotlintry.ui.base.BaseFragment

class PersonFragment:BaseFragment() {

    companion object{
        private const val TAG = "PersonFragment"
    }

    private val roomViewModel : RoomViewModel by viewModels {
        RoomViewModelFactory((mActivity?.application as App).roomRepository)
    }
    private val personAdapter by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        RoomPersonAdapter()
    }

    private var recycler :RecyclerView ?= null
    private var btn_get :Button ?= null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_person_room,container,false)

        view?.apply {
            recycler = findViewById(R.id.person_recycle)
            btn_get = findViewById(R.id.person_btn_get)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = personAdapter
        }
        btn_get?.setOnClickListener {
            roomViewModel.getAllPerson()
        }

        roomViewModel.personLiveData.observe(viewLifecycleOwner){
            personAdapter.submitList(it)
        }
    }
}