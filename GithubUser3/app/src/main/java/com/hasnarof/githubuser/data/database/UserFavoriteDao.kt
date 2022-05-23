package com.hasnarof.githubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hasnarof.githubuser.domain.models.UserFavorite

@Dao
interface UserFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userFavorite: UserFavorite)

    @Update
    fun update(userFavorite: UserFavorite)

    @Query("DELETE FROM user_favorite WHERE username = :username ")
    fun delete(username: String?)

    @Query("SELECT * from user_favorite ORDER BY id ASC")
    fun getAllUserFavorites(): LiveData<List<UserFavorite>>

    @Query("SELECT * FROM user_favorite WHERE username = :username")
    fun findUserFavorite(username: String?): LiveData<List<UserFavorite>>
}