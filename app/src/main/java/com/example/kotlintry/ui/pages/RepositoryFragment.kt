package com.example.kotlintry.ui.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.kotlintry.MainActivity
import com.example.kotlintry.R
import com.example.kotlintry.databinding.FragmentRepositoryBinding
import com.example.kotlintry.ui.base.BaseFragment
import com.example.kotlintry.ui.state.RepositoryState
import com.example.kotlintry.viewModel.*


class RepositoryFragment : BaseFragment(){

    companion object{
        private const val TAG = "RepositoryFragment"
    }

    private var repositoryBinding :FragmentRepositoryBinding ?= null
    private val repositoryState :RepositoryState by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        getFragmentViewModelProvider(this)[RepositoryState::class.java]
    }

    private var firstViewModel : FirstViewModel ?= null
    private var secondViewModel : SecondViewModel ?= null
    private var thirdViewModel : ThirdViewModel ?= null
    private var fourthViewModel : FourthViewModel ?= null
    private var fifthViewModel : FifthViewModel ?= null
    private var sixthViewModel : SixthViewModel ?= null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_repository,container,false)

        firstViewModel = getFragmentViewModelProvider(this)[FirstViewModel::class.java]
        secondViewModel = getFragmentViewModelProvider(this)[SecondViewModel::class.java]
        thirdViewModel = getFragmentViewModelProvider(this)[ThirdViewModel::class.java]
        fourthViewModel = getFragmentViewModelProvider(this)[FourthViewModel::class.java]
        fifthViewModel = getFragmentViewModelProvider(this)[FifthViewModel::class.java]
        sixthViewModel = getFragmentViewModelProvider(this)[SixthViewModel::class.java]

        repositoryBinding = FragmentRepositoryBinding.bind(view)
        repositoryBinding?.apply {
            vm = repositoryState
            click = ClickProxy()
            rep1 = firstViewModel
            rep2 = secondViewModel
            rep3 = thirdViewModel
            rep4 = fourthViewModel
            rep5 = fifthViewModel
            rep6 = sixthViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstViewModel?.listData?.observe(viewLifecycleOwner){
//            Log.i(TAG, "onCreate: ${it.archives}")
            repositoryBinding?.repositoryText?.text = it.archives.toString()
        }

        secondViewModel?.listData?.observe(viewLifecycleOwner){
//            Log.i(TAG, "onCreate: ${it.archives}")
            repositoryBinding?.repositoryText?.text = it.archives.toString()
        }

        thirdViewModel?.listData?.observe(viewLifecycleOwner){
//            Log.i(TAG, "onCreate: ${it.archives}")
            repositoryBinding?.repositoryText?.text = it.archives.toString()
        }

        fourthViewModel?.listData?.observe(viewLifecycleOwner){
//            Log.i(TAG, "onCreate: ${it.archives}")
            repositoryBinding?.repositoryText?.text = it.archives.toString()
        }
        fifthViewModel?.listData?.observe(viewLifecycleOwner){ result ->
            result.onSuccess {
                repositoryBinding?.repositoryText?.text = it.archives.toString()
            }
        }
        sixthViewModel?.listData?.observe(viewLifecycleOwner){
//            Log.i(TAG, "onCreate: ${it.archives}")
            repositoryBinding?.repositoryText?.text = it.archives.toString()
        }
    }

    inner class ClickProxy{
        fun useRep1(){
            firstViewModel?.getListByCoroutine(mapOf("mid" to "11736402","season_id" to "23870",
                "sort_reverse" to "false","page_num" to "1","page_size" to "30"))
        }

        fun useRep2(){
            secondViewModel?.getList(mapOf("mid" to "11736402","season_id" to "23870",
                "sort_reverse" to "false","page_num" to "1","page_size" to "30"))
            {
                Log.i(TAG, "useRep2: success invoke")
            }
        }

        fun useRep3(){
//            thirdViewModel?.getListByCoroutine(mapOf("mid" to "11736402","season_id" to "23870",
//                "sort_reverse" to "false","page_num" to "1","page_size" to "30"))
            thirdViewModel?.getListByFlow(mapOf("mid" to "11736402","season_id" to "23870",
                "sort_reverse" to "false","page_num" to "1","page_size" to "30"),
                {
                    showShortToast("success")
//                    Toast.makeText(this@MainActivity,"success", Toast.LENGTH_SHORT).show()
                },
                {
                    Log.i(TAG, "useRep3: error") // 这里可写可不写
                })
        }

        fun useRep4(){
            fourthViewModel?.getList(mapOf("mid" to "11736402","season_id" to "23870",
                "sort_reverse" to "false","page_num" to "1","page_size" to "30"))
        }

        fun useRep5(){
            fifthViewModel?.getList(mapOf("mid" to "11736402","season_id" to "23870",
                "sort_reverse" to "false","page_num" to "1","page_size" to "30"))
        }

        fun useRep6(){
//            sixthViewModel?.getListFlow/*getListByT*/ /*getListByResult*/ /*getList*/ (mapOf("mid" to "11736402","season_id" to "23870",
//                "sort_reverse" to "false","page_num" to "1","page_size" to "30"))
            sixthViewModel?.getListFlowByState(
                mapOf("mid" to "11736402","season_id" to "23870",
                    "sort_reverse" to "false","page_num" to "1","page_size" to "30"),
                ifSuccess = {
                    showShortToast("success")
//                    Toast.makeText(this@MainActivity,"success", Toast.LENGTH_SHORT ).show()
                },
                ifError = {
                    Log.e(TAG, "useRep6: $it?")
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        repositoryBinding = null
    }
}