package com.example.kotlintry.roomTry

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE

//{
//    "code":0,
//    "message":"0",
//    "ttl":1,
//    "data":{
//    "title":"入站必刷",
//    "media_id":496307088,
//    "explain":"我不允许还有人没看过这85个宝藏视频！",
//    "list":[
//    {
//        "aid":715024588,
//        "videos":1,
//        "tid":233,
//        "tname":"极客DIY",
//        "copyright":1,
//        "pic":"http://i1.hdslb.com/bfs/archive/55ce9a4d1797ec56a0d4ed727f1a279b89ec3664.jpg",
//        "title":"【才浅】15天花20万元用500克黄金敲数万锤纯手工打造三星堆黄金面具",
//        "pubdate":1618207101,
//        "ctime":1618207101,
//        "desc":"倾家荡产求三连支持！！！请大家帮忙给新系列想个名字，点赞一百万的话制作三星堆黄金权杖，不会真的可以点到一百万吧\nbgm:\n-Old-B - 【Free Beat】侠之道 、于剑飞 - 01 片头曲 帝陵、AniFace - 夜辞秋江",
//        "state":0,
//        "duration":717,
//        "mission_id":16881,
//        "rights":{
//        "bp":0,
//        "elec":0,
//        "download":0,
//        "movie":0,
//        "pay":0,
//        "hd5":0,
//        "no_reprint":1,
//        "autoplay":1,
//        "ugc_pay":0,
//        "is_cooperation":0,
//        "ugc_pay_preview":0,
//        "no_background":0,
//        "arc_pay":0,
//        "pay_free_watch":0
//    },
//        "owner":{
//        "mid":2200736,
//        "name":"才疏学浅的才浅",
//        "face":"http://i2.hdslb.com/bfs/face/1f4819b224cd882025a9a6a5f2c6680c332f37bc.jpg"
//    },
//        "stat":{
//        "aid":715024588,
//        "view":13833908,
//        "danmaku":215105,
//        "reply":39792,
//        "favorite":619513,
//        "coin":2484204,
//        "share":122302,
//        "now_rank":0,
//        "his_rank":1,
//        "like":2338710,
//        "dislike":0
//    },
//        "dynamic":"倾家荡产求三连！！",
//        "cid":323723441,
//        "dimension":{
//        "width":1920,
//        "height":1080,
//        "rotate":0
//    },
//        "season_id":19992,
//        "short_link":"https://b23.tv/BV16X4y1g7wT",
//        "short_link_v2":"https://b23.tv/BV16X4y1g7wT",
//        "bvid":"BV16X4y1g7wT",
//        "season_type":0,
//        "is_ogv":false,
//        "ogv_info":null,
//        "rcmd_reason":"",
//        "achievement":"央视新华社点赞，博物馆喊话来上班！"
//    }
//    ]
//}
//}

data class PreciousVideoData (
    val title: String,
    val mediaID: Long,
    val explain: String,
    val list: List<PreciousVideo>
)

@Entity(
    tableName = "precious_video",
    //https://www.jianshu.com/p/cf05150335df?utm_campaign=haruki&utm_content=note&utm_medium=reader_share&utm_source=qq
//    foreignKeys =
//    [ForeignKey(entity = Owner::class,
//            parentColumns = arrayOf("mid"),
//        childColumns = arrayOf("bvid"),
//        onDelete = ForeignKey.CASCADE)]
)
data class PreciousVideo (
    var aid: Long,

    var tid: Int,
    var tname: String,
    @Ignore
    var copyright: Int = 0,
    var pic: String,
    var title: String,
    var pubdate: Long,
    var ctime: Long,
    var desc: String,
    @Ignore
    var state: Int = 0,
    var duration: Long,
    @Ignore
    var missionID: Int = 0,
    @Ignore
    var rights: Map<String, Long> = mapOf(),
    @Embedded
    var owner: Owner,
    @Ignore
    var stat: Map<String, Long> = mapOf(),
    var dynamic: String,
    var cid: Long,
    @Embedded
    var dimension: Dimension,
    @Ignore
    var seasonID: Long = 0,
    var shortLink: String,
    var shortLinkV2: String,
    @PrimaryKey
    var bvid: String,
    var seasonType: Long,
    @Ignore
    var isOgv: Boolean = false,
    @Ignore
    var ogvInfo: Any? = null,
    @Ignore
    var rcmdReason: String = "nothing",
    var achievement: String
){
    constructor():this(0L,0,"0",0,"0","",0,0,"",0,0,0, mapOf(), Owner(0,"",""), mapOf(),"",
    0,Dimension(0,0,0),0,"","","",0,false,null,"","")
}


data class Dimension (
    var width: Long,
    var height: Long,
    var rotate: Long
)

@Entity(tableName = "owner")
data class Owner (
    @PrimaryKey
    var mid: Long,
    var name: String,
    var face: String
)

