package com.example.githubuser.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.network.response.SearchResponse
import com.example.githubuser.network.response.UserResponse
import com.example.githubuser.network.service.ApiConfig
import retrofit2.Call
import retrofit2.Response

class HomeViewModel: ViewModel() {

    val listUser = MutableLiveData<ArrayList<UserResponse>>()

    fun setSearchUser(query: String){
        ApiConfig.getApiService()
            .getSearchUsers(query)
            .enqueue(object : retrofit2.Callback<SearchResponse>{
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getSearchUser(): LiveData<ArrayList<UserResponse>> {
        return listUser
    }
}