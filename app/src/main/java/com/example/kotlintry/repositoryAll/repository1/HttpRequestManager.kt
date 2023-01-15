package com.example.kotlintry.repositoryAll.repository1

import android.util.Log
import androidx.lifecycle.MutableLiveData

import com.example.kotlintry.data.ArchiveData
import com.example.kotlintry.data.BilibiliMsg
import com.example.kotlintry.data.ResponseWrapper

import com.example.kotlintry.repositoryAll.repository1.api.BilibiliApi
import com.example.kotlintry.repositoryAll.repository1.net.APIClient
import com.example.kotlintry.repositoryAll.repository1.net.APIResponse

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

// 这玩意是仓库角色
class HttpRequestManager private constructor() : IRemoteRequest {
    companion object {
        val instance = HttpRequestManager()
        const val TAG = "HttpRequestManager"
    }

    // 暂无使用到
    var responseCodeLiveData: MutableLiveData<String>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }
        private set


    override fun getArchiveList(map: Map<String, String>, liveData: MutableLiveData<ArchiveData>) {

        APIClient.instance.instanceRetrofitWithRxJava(BilibiliApi::class.java, BilibiliApi.BASE_URL)
            .getArchivesList(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                ResponseWrapper(it,0,"error")
            }
//            .subscribe(object :Observer<BilibiliMsg<ArchiveData>>{
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: BilibiliMsg<ArchiveData>) {
//                    Log.i(TAG, "onNext: ${t.data.toString()}")
//                }
//
//                override fun onError(e: Throwable) {
//
//                }
//
//                override fun onComplete() {
//
//                }
//            })
            .subscribe(object : APIResponse<BilibiliMsg<ArchiveData>>() {
                override fun success(data: BilibiliMsg<ArchiveData>?) {
                    Log.i(TAG, "success: ${data!!.data.archives}")
                    liveData.postValue(data.data)
                }

                override fun failure(errorMsg: String?) {
                    Log.i(TAG, "failure: $errorMsg")
                }

            })
    }

    override suspend fun getArchiveListByCoroutine(
        map: Map<String, String>
    ): ArchiveData {
        return APIClient.instance.instanceRetrofitWithRxJava(BilibiliApi::class.java, BilibiliApi.BASE_URL)
            .getListByCoroutine(map).data
    }
}