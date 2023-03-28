package com.example.kotlintry.ui.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintry.App
import com.example.kotlintry.R
import com.example.kotlintry.roomTry.RoomOwnerAdapter
import com.example.kotlintry.roomTry.RoomViewModel
import com.example.kotlintry.roomTry.RoomViewModelFactory
import com.example.kotlintry.ui.base.BaseFragment
import com.example.kotlintry.ui.state.OwnerState
import kotlinx.coroutines.launch

class OwnerFragment:BaseFragment() {
    companion object{
        private const val TAG = "OwnerFragment"
    }

    private val ownerState :OwnerState by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        getFragmentViewModelProvider(this)[OwnerState::class.java]
    }
    private val roomViewModel : RoomViewModel by viewModels {
        RoomViewModelFactory((mActivity?.application as App).roomRepository)
    }
    private val ownerAdapter by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        RoomOwnerAdapter()
    }

    private var recycler :RecyclerView ?= null
    private var btn_get :Button ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_owner_room,container,false)

        view.apply {
            recycler = findViewById(R.id.owner_recycle)
            btn_get = findViewById(R.id.owner_btn_get)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ownerAdapter
        }
        btn_get?.setOnClickListener {
            roomViewModel.getAllOwner()
        }
        lifecycleScope.launch {
            roomViewModel.ownerSharedFlow.collect(){
                Log.i(TAG, "onCreate: 接收到sharedFlow数据")
                ownerAdapter.submitList(it)
            }
        }
    }

}