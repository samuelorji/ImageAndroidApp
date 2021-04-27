package com.example.imageapp.data

import androidx.paging.PagingSource
import com.example.imageapp.api.UnsplashApi
import com.example.imageapp.models.Image
import java.lang.Exception

class UnsplashImagePageSource(
    private val unsplashApi :UnsplashApi,
    private val dataQuery : String
) : PagingSource<Int, Image>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val currentPosition = params.key ?: 1 // null if first position
        return try {
            val images = unsplashApi.searchPhotos(dataQuery,currentPosition,params.loadSize).results

            LoadResult.Page(
                data = images,
                prevKey =  if(currentPosition == 1) null else currentPosition - 1,
                nextKey = if(images.isEmpty()) null else currentPosition + 1 // stop searching when there are no more photos
            )

        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}