package com.hasnarof.githubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hasnarof.githubuser.domain.models.UserFavorite

@Database(entities = [UserFavorite::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun userFavoriteDao(): UserFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GithubDatabase {
            if (INSTANCE == null) {
                synchronized(GithubDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GithubDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as GithubDatabase
        }
    }
}