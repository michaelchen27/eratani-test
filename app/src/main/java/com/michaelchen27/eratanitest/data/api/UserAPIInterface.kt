package com.michaelchen27.eratanitest.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPIInterface {
    // https://gorest.co.in/public/v2/users

    // GET Request with Access Token gives personally added entries.
    @GET("users")
    fun getUsers(@Query("access-token") token: String): Call<ResponseBody>

    @POST("users")
    @FormUrlEncoded
    fun postUser(
        @Query("access-token") token: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("gender") gender: String,
        @Field("status") status: String
    ): Call<ResponseBody>


}