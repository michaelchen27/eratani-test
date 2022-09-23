package com.michaelchen27.eratanitest.data.vo


import com.google.gson.annotations.SerializedName

data class MovieItem(
    @SerializedName("category")
    val category: String,
    @SerializedName("download_count")
    val downloadCount: Int,
    @SerializedName("title")
    val title: String
)