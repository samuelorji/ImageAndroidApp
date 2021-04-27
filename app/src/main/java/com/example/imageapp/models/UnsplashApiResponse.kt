package com.example.imageapp.models

import com.google.gson.annotations.SerializedName


data class UnsplashApiResponse (

    @SerializedName("total") var total : Int,
    @SerializedName("total_pages") var totalPages : Int,
    @SerializedName("results") var results : List<Image>

)