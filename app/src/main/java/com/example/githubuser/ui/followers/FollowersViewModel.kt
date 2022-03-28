package com.example.githubuser.ui.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.network.response.UserResponse
import com.example.githubuser.network.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {

    private val listFollowers = MutableLiveData<ArrayList<UserResponse>>()

    fun setListFollowers(username: String){
        ApiConfig.getApiService()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<UserResponse>>{
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

    fun getListFollowers(): LiveData<ArrayList<UserResponse>> {
        return listFollowers
    }
}