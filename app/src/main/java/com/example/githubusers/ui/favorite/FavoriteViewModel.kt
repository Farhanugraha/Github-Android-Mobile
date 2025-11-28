package com.example.githubusers.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.local.FavoriteUser

class FavoriteViewModel(private val mFavoriteUsersRepository: FavoriteRepository) : ViewModel(){

    init {
        getAllFavoriteUsers()
    }

    fun getAllFavoriteUsers() : LiveData<List<FavoriteUser>> = mFavoriteUsersRepository.getAllFavoriteUsers()
}