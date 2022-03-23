package com.hasnarof.githubuser.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String?,
    var avatarUrl: String?,
    var followersUrl: String?,
    var followingUrl: String?,
) : Parcelable