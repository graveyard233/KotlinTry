package com.example.kotlintry.pagingTry

import com.example.kotlintry.data.BilibiliMsg
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import retrofit2.http.Streaming
import retrofit2.http.Url

interface BilibiliService {
//    @Headers(
////    "cookie: innersign=0; buvid3=B575616A-51B6-ECBF-6E0D-B8121497810663118infoc;" +
////            " b_nut=1677473063; i-wanna-go-back=-1; b_ut=7; " +
////            "b_lsid=453889107_186912FB82B; _uuid=E6C1FB10D-DC67-5101010-72E1-373E3D9E4101669105infoc; header_theme_version=undefined;" +
////            " buvid4=DA2D378F-FB6C-F007-1ACA-E88D4848950663534-023022712-FfApz2RIOL7ETIeuUL5H9Q%3D%3D; buvid_fp=134fcf6f5ba745548b5ecabcc83e8593; " +
////            "home_feed_column=4"
////    "cookie:innersign=0; domain=bilibili.com;  buvid3=469FC80F-9F78-86BB-245E-BE776A7B1AEB46949infoc; expires=Tue, 27 Feb 2024 06:34:06 GMT; domain=bilibili.com; b_nut=1677479646; expires=Tue, 27 Feb 2024 06:34:06 GMT; domain=bilibili.com; "
//    "cookie:innersign=0;  domain=.bilibili.com, buvid3=3A548F03-AC28-97BF-5F5D-BFFEC0DD137260969infoc;  expires=Tue, 27 Feb 2024 07:52:40 GMT; domain=.bilibili.com, b_nut=1677484360;  expires=Tue, 27 Feb 2024 07:52:40 GMT; domain=.bilibili.com"
//    )
    @GET("web-interface/wbi/search/type")
    suspend fun getSearchList(@QueryMap map: Map<String,String>)
    : BilibiliMsg<VideoSearchData<BilibiliVideo>>


    @Streaming
    @GET
    suspend fun getCookie(@Url url: String)
    : ResponseBody
}