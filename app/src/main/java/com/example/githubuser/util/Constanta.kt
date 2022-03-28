package com.example.githubuser.util

import androidx.annotation.StringRes
import com.example.githubuser.R

object Constanta {
    const val EXTRA_USERNAME = "extra_username"
    const val EXTRA_ID = "extra_id"
    const val EXTRA_AVATAR = "extra_avatar"

    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.follower,
        R.string.following
    )

    const val GITHUB_TOKEN = "Authentication: token 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"

    const val BASE_URL = "https://api.github.com"
}