package com.example.kotlintry.repositoryAll.repository1.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

/**
 * 访问服务器的API接口(WanAndroidAPI) 的 客户端管理
 *
 * Retrofit封装好，同学们自己看
 */
class APIClient {

    private object Holder {

        val INSTANCE = APIClient()

    }

    // 派生
    companion object {

        val instance = Holder.INSTANCE

    }

    // WanAndroidAPI实例化这个，  XXXAPI实例化这个，   BBBAPI实例化
    fun <T> instanceRetrofitWithRxJava(apiInterface: Class<T>,baseUrl : String) : T {

        // 之前的代码
        /*val mOkHttpClient: OkHttpClient = OkHttpClient().newBuilder()

            // 添加读取超时时间
            .readTimeout(10000, TimeUnit.SECONDS)

            // 添加连接超时时间
            .connectTimeout(10000, TimeUnit.SECONDS)

            // 添加写出超时时间
            .writeTimeout(10000, TimeUnit.SECONDS)

            .build()*/

        // OKHttpClient请求服务器
        val mOkHttpClient = OkHttpClient().newBuilder().myApply {

            // 添加读取超时时间
            readTimeout(10000, TimeUnit.SECONDS)

            // 添加连接超时时间
            connectTimeout(10000, TimeUnit.SECONDS)

            // 添加写出超时时间
            writeTimeout(10000, TimeUnit.SECONDS)

            addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })

        }.build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)

        // 请求方  ←
            .client(mOkHttpClient)

        // 响应方  →
            // Response的事情  回来
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // RxJava来处理
            .addConverterFactory(GsonConverterFactory.create()) // Gson 来解析 --- JavaBean
            .build()

        return retrofit.create(apiInterface);
    }
}

// 默认 无参数的
fun <T> T.myApply(mm: T.() -> Unit) : T {
    // T == this
    mm()
    return this
}