package com.hasnarof.githubuser.data.network

import com.hasnarof.githubuser.BuildConfig
import com.hasnarof.githubuser.data.network.response.FollowersResponse
import com.hasnarof.githubuser.data.network.response.FollowingResponse
import com.hasnarof.githubuser.data.network.response.UserDetailResponse
import com.hasnarof.githubuser.data.network.response.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun searchUser(
        @Query("q") q: String?
    ): Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUser(
        @Path("username") username: String?
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserFollowers(
        @Path("username") username: String?
    ): Call<List<FollowersResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserFollowing(
        @Path("username") username: String?
    ): Call<List<FollowingResponse>>
}