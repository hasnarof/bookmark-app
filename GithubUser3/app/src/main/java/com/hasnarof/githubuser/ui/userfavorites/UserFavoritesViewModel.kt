package com.hasnarof.githubuser.ui.userfavorites

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hasnarof.githubuser.domain.models.UserFavorite
import com.hasnarof.githubuser.repository.UserFavoriteRepository

class UserFavoritesViewModel(application: Application) : ViewModel() {

    private val mUserFavoriteRepository: UserFavoriteRepository = UserFavoriteRepository(application)

    fun getAllUserFavorites(): LiveData<List<UserFavorite>> = mUserFavoriteRepository.getAllUserFavorites()

    fun insert(userFavorite: UserFavorite) {
        mUserFavoriteRepository.insert(userFavorite)
    }

    fun update(userFavorite: UserFavorite) {
        mUserFavoriteRepository.update(userFavorite)
    }

    fun delete(username: String?) {
        mUserFavoriteRepository.delete(username)
    }
}