package com.michaelchen27.eratanitest.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPIInterface {
    // https://gorest.co.in/public/v2/users

    @GET("users")
    fun getUsers(): Call<ResponseBody>

}