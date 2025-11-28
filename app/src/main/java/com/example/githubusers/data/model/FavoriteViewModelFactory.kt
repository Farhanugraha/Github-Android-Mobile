package com.example.githubusers.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.ui.favorite.FavoriteRepository
import com.example.githubusers.ui.favorite.FavoriteViewModel

class FavoriteViewModelFactory(private val repo: FavoriteRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) return DetailUserViewModel(repo) as T
        else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) return FavoriteViewModel(repo) as T
        throw IllegalArgumentException("Unknowns viewModel: ${modelClass.name}")

    }
}