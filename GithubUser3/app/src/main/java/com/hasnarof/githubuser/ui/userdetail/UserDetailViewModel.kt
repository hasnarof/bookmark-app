package com.hasnarof.githubuser.ui.userdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasnarof.githubuser.data.network.ApiConfig
import com.hasnarof.githubuser.data.network.response.FollowersResponse
import com.hasnarof.githubuser.data.network.response.FollowingResponse
import com.hasnarof.githubuser.data.network.response.UserDetailResponse
import com.hasnarof.githubuser.domain.models.User
import com.hasnarof.githubuser.domain.models.UserDetail
import com.hasnarof.githubuser.domain.models.UserFavorite
import com.hasnarof.githubuser.repository.UserFavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserDetailViewModel(application: Application) : ViewModel() {

    private val _user = MutableLiveData<UserDetail>()
    val user: LiveData<UserDetail> = _user

    private val _followers = MutableLiveData<List<User>>()
    val followers: LiveData<List<User>> = _followers

    private val _following = MutableLiveData<List<User>>()
    val following: LiveData<List<User>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mUserFavoriteRepository: UserFavoriteRepository = UserFavoriteRepository(application)


    companion object {
        private const val TAG = "UserDetailViewModel"
    }

    fun getUser(query: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseUser = response.body()

                    _user.value = UserDetail(responseUser?.login, responseUser?.avatarUrl, responseUser?.followersUrl, responseUser?.followingUrl,
                        responseUser?.followers, responseUser?.following, responseUser?.name,responseUser?.company,responseUser?.location, responseUser?.bio, responseUser?.publicRepos)
                } else {
                    Log.e(UserDetailViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(UserDetailViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<FollowersResponse>> {

            override fun onResponse(
                call: Call<List<FollowersResponse>>,
                response: Response<List<FollowersResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val response = response.body()
                    val convertedFollowers = ArrayList<User>()

                    response?.forEach {
                        convertedFollowers.add(User(
                            it.login, it.avatarUrl, it.followersUrl,
                            it.followingUrl
                        ))
                    }

                    _followers.value = convertedFollowers
                } else {
                    Log.e(UserDetailViewModel.TAG, "onFailure: followers ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(UserDetailViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<FollowingResponse>> {

            override fun onResponse(
                call: Call<List<FollowingResponse>>,
                response: Response<List<FollowingResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val response = response.body()
                    val convertedFollowing = ArrayList<User>()

                    response?.forEach {
                        convertedFollowing.add(User(
                            it.login, it.avatarUrl, it.followersUrl,
                            it.followingUrl
                        ))
                    }

                    _following.value = convertedFollowing
                } else {
                    Log.e(UserDetailViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(UserDetailViewModel.TAG, "onFailure: following ${t.message.toString()}")
            }
        })
    }

    fun getAllUserFavorites(): LiveData<List<UserFavorite>> = mUserFavoriteRepository.getAllUserFavorites()

    fun isFavorite(username: String?): LiveData<List<UserFavorite>> {
        return mUserFavoriteRepository.findUserFavorite(username)
    }

    fun insertUserFavorite(userFavorite: UserFavorite) {
        mUserFavoriteRepository.insert(userFavorite)
    }

    fun deleteUserFavorite(username: String?) {
        mUserFavoriteRepository.delete(username)
    }

}