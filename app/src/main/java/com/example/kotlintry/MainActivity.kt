package com.example.kotlintry

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels

import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.kotlintry.databinding.ActivityMainBinding
import com.example.kotlintry.pagingTry.PagingViewModel
import com.example.kotlintry.pagingTry.VideoAdapter
import com.example.kotlintry.roomTry.*
import com.example.kotlintry.ui.base.BaseActivity
import com.example.kotlintry.viewModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 这个项目主要用来测试封装和kotlin的各种特性
 * */
@AndroidEntryPoint
class MainActivity : BaseActivity() {
    companion object{
        private const val TAG = "MainActivity"
    }

    private var mainBinding : ActivityMainBinding ?= null

    private var navController :NavController ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        mainBinding?.apply {
            lifecycleOwner = this@MainActivity
//            vm = firstViewModel
            click = ClickProxy()
        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        mainBinding?.mainNavView?.setupWithNavController(navController!!)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_paging,R.id.nav_repository,R.id.nav_room,R.id.nav_hilt), drawerLayout = mainBinding?.mainDrawer)
        mainBinding?.mainToolbar?.setupWithNavController(navController!!,appBarConfiguration)

    }

    inner class ClickProxy{

    }


}