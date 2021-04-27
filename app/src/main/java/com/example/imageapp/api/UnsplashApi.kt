package com.example.imageapp.api

import com.example.imageapp.models.UnsplashApiResponse
import com.example.imageapp.utils.Config
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${Config.UNSPLASH_API_KEY}")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashApiResponse
}