package com.example.githubuser.network.service

import com.example.githubuser.network.response.SearchResponse
import com.example.githubuser.network.response.UserDetailResponse
import com.example.githubuser.network.response.UserResponse
import com.example.githubuser.util.Constanta.GITHUB_TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers(GITHUB_TOKEN)
    fun getSearchUsers(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers(GITHUB_TOKEN)
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/following")
    @Headers(GITHUB_TOKEN)
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserResponse>>

    @GET("users/{username}/followers")
    @Headers(GITHUB_TOKEN)
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserResponse>>
}