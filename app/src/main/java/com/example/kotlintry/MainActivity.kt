package com.example.kotlintry

import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kotlintry.databinding.ActivityMainBinding
import com.example.kotlintry.pagingTry.PagingViewModel
import com.example.kotlintry.repositoryAll.repository1.HttpRequestManager
import com.example.kotlintry.viewModel.*

/**
 * 这个项目主要用来测试封装和kotlin的各种特性
 * */

//    https://api.bilibili.com/x/polymer/space/seasons_archives_list?mid=11736402&season_id=23870&sort_reverse=false&page_num=1&page_size=30

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MainActivity"
    }

    private var mainBinding : ActivityMainBinding ?= null

    private var mainViewModel : MainActivityViewModel ?= null
    private var secondViewModel : SecondViewModel ?= null
    private var thirdViewModel : ThirdViewModel ?= null
    private var fourthViewModel : FourthViewModel ?= null
    private var fifthViewModel : FifthViewModel ?= null
    private var sixthViewModel : SixthViewModel ?= null

    private var pagingViewModel : PagingViewModel ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this,this.defaultViewModelProviderFactory)[MainActivityViewModel::class.java]
        secondViewModel = ViewModelProvider(this,this.defaultViewModelProviderFactory)[SecondViewModel::class.java]
        thirdViewModel = ViewModelProvider(this,this.defaultViewModelProviderFactory)[ThirdViewModel::class.java]
        fourthViewModel = ViewModelProvider(this,this.defaultViewModelProviderFactory)[FourthViewModel::class.java]
        fifthViewModel = ViewModelProvider(this,this.defaultViewModelProviderFactory)[FifthViewModel::class.java]
        sixthViewModel = ViewModelProvider(this,this.defaultViewModelProviderFactory)[SixthViewModel::class.java]
        pagingViewModel = ViewModelProvider(this,this.defaultViewModelProviderFactory)[PagingViewModel::class.java]
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        mainBinding?.apply {
            lifecycleOwner = this@MainActivity
            vm = mainViewModel
            click = ClickProxy()
        }

        mainViewModel?.listData?.observe(this){
//            Log.i(TAG, "onCreate: ${it.archives}")
            mainBinding!!.textView1.text = it.archives.toString()
        }

        secondViewModel?.listData?.observe(this){
//            Log.i(TAG, "onCreate: ${it.archives}")
            mainBinding!!.textView1.text = it.archives.toString()
        }

        thirdViewModel?.listData?.observe(this){
//            Log.i(TAG, "onCreate: ${it.archives}")
            mainBinding!!.textView1.text = it.archives.toString()
        }

        fourthViewModel?.listData?.observe(this){
//            Log.i(TAG, "onCreate: ${it.archives}")
            mainBinding!!.textView1.text = it.archives.toString()
        }
        fifthViewModel?.listData?.observe(this){ result ->
            result.onSuccess {
                mainBinding!!.textView1.text = it.archives.toString()
            }
        }
        sixthViewModel?.listData?.observe(this){
//            Log.i(TAG, "onCreate: ${it.archives}")
            mainBinding!!.textView1.text = it.archives.toString()
        }

        pagingViewModel?.getCookie()
    }

    inner class ClickProxy{
        fun useRep1(){
            mainViewModel?.getListByCoroutine(mapOf("mid" to "11736402","season_id" to "23870",
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
                    Toast.makeText(this@MainActivity,"success",Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this@MainActivity,"success",Toast.LENGTH_SHORT ).show()
                },
                ifError = {
                    Log.e(TAG, "useRep6: $it?")
                }
            )
        }

        fun usePagingRep(){
            val pageMap = mutableMapOf("page" to "1",
                "page_size" to "10",
            "order" to "pubdate",
            "keyword" to "赦免者",
            "search_type" to "video")
            pagingViewModel?.getListFlow(pageMap)
//            pagingViewModel?.getCookie()
        }

    }


}