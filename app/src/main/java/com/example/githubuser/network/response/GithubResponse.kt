package com.example.githubuser.network.response

import com.google.gson.annotations.SerializedName

data class SearchResponse (

    val items: ArrayList<UserResponse>

)

data class UserResponse (

    @field:SerializedName("login")
    val username: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("avatar_url")
    val avatar: String? = null,

)

data class UserDetailResponse(

    @field:SerializedName("login")
    val username: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("avatar_url")
    val avatar: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("followers_url")
    val followers_url: String? = null,

    @field:SerializedName("following_url")
    val following_url: String? = null,

    @field:SerializedName("public_repos")
    val repositories: Int? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

)
