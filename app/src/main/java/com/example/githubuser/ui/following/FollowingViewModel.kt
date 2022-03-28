package com.example.githubuser.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.network.response.UserResponse
import com.example.githubuser.network.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    private val listFollowers = MutableLiveData<ArrayList<UserResponse>>()

    fun setListFollowing(username: String){
        ApiConfig.getApiService()
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<UserResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<UserResponse>>,
                    response: Response<ArrayList<UserResponse>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<UserResponse>> {
        return listFollowers
    }

}