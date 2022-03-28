package com.example.githubuser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login: String,
    val avatarUrl: String,
    @PrimaryKey
    val id: Int
): Serializable
