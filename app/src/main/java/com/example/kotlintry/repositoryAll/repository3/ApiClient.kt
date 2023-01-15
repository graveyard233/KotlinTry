package com.example.kotlintry.repositoryAll.repository3

object ApiClient {

    @JvmStatic
    val bilibiliApi: BilibiliApi by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        RetrofitClient.retrofitClient.create(BilibiliApi::class.java)
    }

    // 这里可以拓展其他的api，但baseUrl都一样，
    // 假如需要不同的话可以在这一层进行分类讨论

}