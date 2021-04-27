package com.example.imageapp.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.imageapp.api.UnsplashApi
import com.example.imageapp.data.UnsplashImagePageSource
import com.example.imageapp.models.Image
import com.example.imageapp.utils.Config
import javax.inject.Inject

class ImageRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun searchFor(query : String): LiveData<PagingData<Image>> {
        return Pager(
            config = PagingConfig(
                pageSize = Config.IMAGES_PAGE_SIZE,
                maxSize = Config.IMAGES_MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashImagePageSource(unsplashApi,query)}
        ).liveData

    }
}