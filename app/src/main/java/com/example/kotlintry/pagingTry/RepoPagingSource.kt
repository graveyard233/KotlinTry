package com.example.kotlintry.pagingTry

import androidx.paging.PagingSource
import androidx.paging.PagingState

class RepoPagingSource : PagingSource<Int, BilibiliVideo>() {
    override fun getRefreshKey(state: PagingState<Int, BilibiliVideo>): Int? =null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BilibiliVideo> {
        return try {
            val page = params.key ?: 1// set page 1 as default
            val pageSize = params.loadSize
            val response = BilibiliApi.service.getSearchList(mutableMapOf(
                "page" to page.toString(),
                "page_size" to pageSize.toString(),
                "order" to "pubdate",
                "keyword" to "赦免者",
                "search_type" to "video"
            ))
            val repoItems = response.data.result
            val prevKey = if(page>1) - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems,prevKey,nextKey)
        }catch (e :Exception){
            LoadResult.Error(e)
        }
    }

}