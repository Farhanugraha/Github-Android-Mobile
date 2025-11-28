package com.example.githubusers.data.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.ViewModel
import com.example.githubusers.api.ApiConfig
import com.example.githubusers.data.local.FavoriteUser
import com.example.githubusers.data.local.FavoriteUserDao
import com.example.githubusers.data.local.UserDatabase
import com.example.githubusers.data.response.DetailUserResponse
import com.example.githubusers.ui.favorite.FavoriteRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserViewModel(private val mFavoriteUserRepository: FavoriteRepository) : ViewModel() {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun addFavoriteUser(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            mFavoriteUserRepository.insert(favoriteUser)
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUserRepository.getFavoriteUserByUsername(username)

    fun deleteFavoriteUser(favUser: FavoriteUser) {
        viewModelScope.launch {
            mFavoriteUserRepository.delete(favUser)
        }
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    companion object {
        private const val TAG = "DetailUserViewModel"
    }
}
