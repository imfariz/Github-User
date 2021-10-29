package com.dicoding.submission1_fundamentalandroid.api

import com.google.gson.annotations.SerializedName

data class FollowResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String
)
