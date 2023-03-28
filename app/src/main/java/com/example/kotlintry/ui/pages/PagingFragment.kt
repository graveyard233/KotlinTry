package com.example.kotlintry.ui.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlintry.R
import com.example.kotlintry.databinding.FragmentPagingBinding
import com.example.kotlintry.pagingTry.FooterAdapter
import com.example.kotlintry.pagingTry.PagingViewModel
import com.example.kotlintry.pagingTry.VideoAdapter
import com.example.kotlintry.ui.base.BaseFragment
import com.example.kotlintry.ui.state.PagingState
import kotlinx.coroutines.launch

class PagingFragment :BaseFragment() {
    companion object{
        private const val TAG = "PagingFragment"
    }

    private var pagingBinding :FragmentPagingBinding ?= null
    private val pagingState:PagingState by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        getFragmentViewModelProvider(this)[PagingState::class.java]
    }
    private var pagingViewModel : PagingViewModel ?= null
    private var videoAdapter :VideoAdapter ?= null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_paging,container,false)

        pagingBinding = FragmentPagingBinding.bind(view)
        pagingBinding?.apply {
            vm = pagingState
            click = ClickProxy()
            lifecycleOwner = viewLifecycleOwner
        }
        pagingViewModel = getFragmentViewModelProvider(this)[PagingViewModel::class.java]
        videoAdapter = VideoAdapter(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagingViewModel?.getCookie()
        pagingBinding?.pagingRecycler?.layoutManager = LinearLayoutManager(requireContext())
//         这里是paging的adapter，要看的时候就取消注释就行
        pagingBinding?.pagingRecycler?.adapter = videoAdapter?.withLoadStateFooter(FooterAdapter{
            videoAdapter?.retry()
        })
        videoAdapter?.addLoadStateListener {
            when(it.refresh){
                is LoadState.NotLoading ->{

                }
                is LoadState.Loading ->{

                }
                is LoadState.Error ->{
                    val state = it.refresh as LoadState.Error
                    Log.e(TAG, "onCreate: ${state.error.message}")
                }
            }
        }
    }

    inner class ClickProxy{

        fun usePagingRep(){
//            val pageMap = mutableMapOf("page" to "1",
//                "page_size" to "10",
//                "order" to "pubdate",
//                "keyword" to "赦免者",
//                "search_type" to "video")
//            pagingViewModel?.getListFlow(pageMap)
//            pagingViewModel?.getCookie()
            lifecycleScope.launch {
                pagingViewModel?.getPagingData()?.collect{pagingData ->
                    videoAdapter?.submitData(pagingData)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pagingBinding = null
    }
}