package com.example.imageapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image (

    @SerializedName("id") val id : String,
    @SerializedName("created_at") val createdAt : String,
//    @SerializedName("width") val width : Int,
//    @SerializedName("height") val height : Int,
//    @SerializedName("color") val color : String,
//    @SerializedName("blur_hash") val blurHash : String,
//    @SerializedName("likes") val likes : Int,
//    @SerializedName("liked_by_user") val likedByUser : Boolean,
    @SerializedName("description") val description : String?,
    @SerializedName("user") val user : User,
//    @SerializedName("current_user_collections") val currentUserCollections : List<String>,
    @SerializedName("urls") val urls : Urls,
//    @SerializedName("links") val links : Links
): Parcelable
@Parcelize
data class Links (

    @SerializedName("self") val self : String,
    @SerializedName("html") val html : String,
    @SerializedName("download") val download : String

): Parcelable
@Parcelize
data class Urls (

    @SerializedName("raw") val raw : String,
    @SerializedName("full") val full : String,
    @SerializedName("regular") val regular : String,
    @SerializedName("small") val small : String,
    @SerializedName("thumb") val thumb : String

): Parcelable

@Parcelize
data class User (

    @SerializedName("id") val id : String,
    @SerializedName("username") val username : String,
//    @SerializedName("name") val name : String,
//    @SerializedName("first_name") val firstName : String,
//    @SerializedName("last_name") val lastName : String,
//    @SerializedName("instagram_username") val instagramUsername : String,
//    @SerializedName("twitter_username") val twitterUsername : String,
//    @SerializedName("portfolio_url") val portfolioUrl : String,
//    @SerializedName("profile_image") val profileImage : ProfileImage,
//    @SerializedName("links") val links : Links

) : Parcelable {
    fun getReferralUrl() = "https://unsplash.com/$username?utm_source=ImageApp&utm_medium=referral"
}