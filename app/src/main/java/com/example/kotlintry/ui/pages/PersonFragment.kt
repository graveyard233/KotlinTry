package com.example.kotlintry.ui.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintry.App
import com.example.kotlintry.R
import com.example.kotlintry.roomTry.*
import com.example.kotlintry.ui.base.BaseFragment

class PersonFragment:BaseFragment() {

    private val roomViewModel : RoomViewModel by viewModels {
        RoomViewModelFactory((mActivity?.application as App).roomRepository)
    }
    private val personAdapter by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        RoomPersonAdapter().apply {
            setOnItemClickListener{adapter,_,position ->
                val action = PersonFragmentDirections.actionPersonFragmentToShowDetailFragment(ArgPerson(adapter.getItem(position)!!))
                nav().navigate(action)
            }
        }
    }

    private var recycler :RecyclerView ?= null
    private var btn_get :Button ?= null
    private var btn_init :Button ?= null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_person_room,container,false)

        view?.apply {
            recycler = findViewById(R.id.person_recycle)
            btn_get = findViewById(R.id.person_btn_get)
            btn_init = findViewById(R.id.person_btn_initTable)
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
        btn_init?.setOnClickListener {
            roomViewModel.initTestTable()
        }

//        roomViewModel.personLiveData.observe(viewLifecycleOwner){
//            personAdapter.submitList(it)
//        }

        // 默认是ALLOW，可以立即恢复状态；若设置为PREVENT_WHEN_EMPTY则可以实现异步加载也能恢复滚动状态
//        personAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        roomViewModel.personStateLiveData.observe(viewLifecycleOwner){
            Log.i(TAG, "onViewCreated: get personStateLiveData")
            personAdapter.submitList(it)
        }
    }
}