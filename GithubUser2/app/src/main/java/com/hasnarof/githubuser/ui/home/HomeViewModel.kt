package com.hasnarof.githubuser.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasnarof.githubuser.data.network.ApiConfig
import com.hasnarof.githubuser.data.network.response.UserSearchResponse
import com.hasnarof.githubuser.domain.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private val _listUsers = MutableLiveData<List<User>>()
    val listUsers: LiveData<List<User>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "HomeViewModel"
    }

    fun searchUser(query: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<UserSearchResponse> {
            override fun onResponse(
                call: Call<UserSearchResponse>,
                response: Response<UserSearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseUsers = response.body()?.items
                    val convertedUsers = ArrayList<User>()

                    responseUsers?.forEach {
                        convertedUsers.add(User(it?.login, it?.avatarUrl, it?.followersUrl, it?.followingUrl))
                    }

                    _listUsers.value = convertedUsers
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}