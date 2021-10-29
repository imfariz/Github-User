package com.dicoding.submission1_fundamentalandroid.api

import com.dicoding.submission1_fundamentalandroid.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): APIService {
            val client by lazy {
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val key = BuildConfig.TOKEN
                        val requestBuilder = original.newBuilder()
                            .header("Authorization", "token $key")
                        val request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .build()
            }

            val retrofit: Retrofit.Builder by lazy {
                Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
            }
            return retrofit.build().create(APIService::class.java)
        }
    }
}