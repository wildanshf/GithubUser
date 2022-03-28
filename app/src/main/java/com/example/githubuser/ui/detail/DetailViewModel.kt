package com.example.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.data.local.FavoriteUser
import com.example.githubuser.data.local.FavoriteUserDao
import com.example.githubuser.data.local.UserDatabase
import com.example.githubuser.network.response.UserDetailResponse
import com.example.githubuser.network.service.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    val user = MutableLiveData<UserDetailResponse>()

    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)
    private var userDao: FavoriteUserDao? = userDb?.favoriteUserDao()

    fun setUserDetail(username: String){
        ApiConfig.getApiService()
            .getUserDetail(username)
            .enqueue(object : Callback<UserDetailResponse>{
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })

    }

    fun getUserDetail(): LiveData<UserDetailResponse>{
        return user
    }

    fun addToFavorite(username: String, id: Int, avatar: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                avatar,
                username,
                id
            )
            userDao?.addFavoriteUser(user)
        }
    }

    suspend fun checkedUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavoriteUser(id)
        }
    }

}