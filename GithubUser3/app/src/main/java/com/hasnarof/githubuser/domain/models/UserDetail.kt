package com.hasnarof.githubuser.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    var username: String?,
    var avatarUrl: String?,
    var followersUrl: String?,
    var followingUrl: String?,
    var followers: Int?,
    var following: Int?,
    var name: String?,
    var company: String?,
    var location: String?,
    var bio: String?,
    var publicRepo: Int?,
) : Parcelable