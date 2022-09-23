package com.michaelchen27.eratanitest.data.vo.user


import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String
)