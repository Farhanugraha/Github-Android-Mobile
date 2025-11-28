package com.example.githubusers.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubusers.data.local.FavoriteUser
import com.example.githubusers.data.local.FavoriteUserDao
import com.example.githubusers.data.local.UserDatabase

class FavoriteRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao

    init {
        val db = UserDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    suspend fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserDao.insert(favoriteUser)
    }

    suspend fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserDao.delete(favoriteUser)
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUserDao.getFavoriteUserByUsername(username)
}