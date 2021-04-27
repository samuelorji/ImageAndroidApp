package com.example.imageapp.utils

import com.example.imageapp.BuildConfig

object Config {
    const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"
    const val UNSPLASH_API_KEY = BuildConfig.UNSPLASH_ACCESS_KEY
    const val IMAGES_MAX_SIZE = 100
    const val IMAGES_PAGE_SIZE = 20

}