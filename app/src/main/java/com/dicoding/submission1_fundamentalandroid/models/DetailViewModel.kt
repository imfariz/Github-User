package com.dicoding.submission1_fundamentalandroid.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission1_fundamentalandroid.api.ApiConfig
import com.dicoding.submission1_fundamentalandroid.api.DetailResponse
import com.dicoding.submission1_fundamentalandroid.api.FollowResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _detailsUser = MutableLiveData<DetailResponse>()
    val detailsUser: LiveData<DetailResponse> = _detailsUser

    private val _followers = MutableLiveData<ArrayList<FollowResponseItem>>()
    val followers: LiveData<ArrayList<FollowResponseItem>> = _followers

    private val _following = MutableLiveData<ArrayList<FollowResponseItem>>()
    val following: LiveData<ArrayList<FollowResponseItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFollow = MutableLiveData<Boolean>()
    val isLoadingFollow: LiveData<Boolean> = _isLoadingFollow

    fun showDetail(id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailsUser(id)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailsUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure Response: ${t.message}")
            }
        })
    }

    fun getFollower(id: String) {
        val client = ApiConfig.getApiService().getFollower(id)
        client.enqueue(object : Callback<ArrayList<FollowResponseItem>> {
            override fun onResponse(call: Call<ArrayList<FollowResponseItem>>,
                                    response: Response<ArrayList<FollowResponseItem>>) {
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure Response: ${t.message}")
            }
        })
    }

    fun getFollowing(id: String) {
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getFollowing(id)
        client.enqueue(object : Callback<ArrayList<FollowResponseItem>> {
            override fun onResponse(call: Call<ArrayList<FollowResponseItem>>,
                                    response: Response<ArrayList<FollowResponseItem>>) {
                _isLoadingFollow.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e(TAG, "onFailure Response: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}