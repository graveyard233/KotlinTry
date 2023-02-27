package com.example.kotlintry.pagingTry

import android.util.Log
import com.example.kotlintry.utils.MMKVUtils
import com.tencent.mmkv.MMKV
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

object MyOkHttpClient {

    val mClient : OkHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        buildClient()
    }

    private fun buildClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(logging)
            followRedirects(true) // 允许失败重定向
            cookieJar(MyCookieJar())
        }.build()
    }

    val cookieClient : OkHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder().apply {
            addInterceptor(logging)
            followRedirects(true)
            cookieJar(object :CookieJar{
                private val cookieStore : HashMap<String,List<Cookie>> = HashMap<String,List<Cookie>>()
                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    return cookieStore[url.host] ?: ArrayList<Cookie>()
                }

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    Log.i("lyd", "$url -> saveFromResponse: $cookies")
                    MMKVUtils.put("mCookie",cookies)
                }
            })
        }.build()
    }
}

class MyCookieJar : CookieJar{
//    private val cookieStore : HashMap<String,List<Cookie>> = HashMap<String,List<Cookie>>()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return MMKVUtils.getList("mCookie")
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        // 这里不要保存，防止覆盖
    }

}
