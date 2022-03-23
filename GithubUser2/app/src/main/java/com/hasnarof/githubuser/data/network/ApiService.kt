package com.hasnarof.githubuser.data.network

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
    @Headers("Authorization: token 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")
    fun searchUser(
        @Query("q") q: String?
    ): Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(
        @Path("username") username: String?
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String?
    ): Call<List<FollowersResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String?
    ): Call<List<FollowingResponse>>
}