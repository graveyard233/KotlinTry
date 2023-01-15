package com.example.kotlintry.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**只为[SecondViewModel]工作*/
abstract class BaseViewModel : ViewModel() {
    /** 请求异常（服务器请求失败，譬如：服务器连接超时等） */
    val exception = MutableLiveData<Exception>()

    /** 界面启动时要进行的初始化逻辑，如网络请求,数据初始化等 */
    abstract fun start()
}