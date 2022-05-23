package com.hasnarof.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.hasnarof.githubuser.data.database.GithubDatabase
import com.hasnarof.githubuser.data.database.UserFavoriteDao
import com.hasnarof.githubuser.domain.models.UserFavorite
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserFavoriteRepository (application: Application) {
    private val mUserFavoriteDao: UserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GithubDatabase.getDatabase(application)
        mUserFavoriteDao = db.userFavoriteDao()
    }

    fun getAllUserFavorites(): LiveData<List<UserFavorite>> = mUserFavoriteDao.getAllUserFavorites()

    fun insert(userFavorite: UserFavorite) {
        executorService.execute { mUserFavoriteDao.insert(userFavorite) }
    }

    fun delete(username: String?) {
        executorService.execute { mUserFavoriteDao.delete(username) }
    }

    fun update(userFavorite: UserFavorite) {
        executorService.execute { mUserFavoriteDao.update(userFavorite) }
    }

    fun findUserFavorite(username: String?): LiveData<List<UserFavorite>> {
        return mUserFavoriteDao.findUserFavorite(username)
    }
}