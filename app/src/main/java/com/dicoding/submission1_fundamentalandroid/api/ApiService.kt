package com.dicoding.submission1_fundamentalandroid.api

import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @GET("search/users")
    fun getListUsers(@Query("q") q: String): Call<UserResponse>

    @GET("users/{id}")
    fun getDetailsUser(@Path("id") id: String): Call<DetailResponse>

    @GET("users/{id}/followers")
    fun getFollower(@Path("id") id: String): Call<ArrayList<FollowResponseItem>>

    @GET("users/{id}/following")
    fun getFollowing(@Path("id") id: String): Call<ArrayList<FollowResponseItem>>
}