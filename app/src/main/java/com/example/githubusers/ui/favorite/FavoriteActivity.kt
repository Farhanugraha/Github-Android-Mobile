package com.example.githubusers.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.data.local.FavoriteUser
import com.example.githubusers.data.model.FavoriteViewModelFactory
import com.example.githubusers.data.model.UserViewModel
import com.example.githubusers.databinding.ActivityFavoriteBinding
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.ui.adapter.UserAdapter
import com.example.githubusers.ui.main.DetailUserActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val userViewModel by viewModels<FavoriteViewModel>(){
        FavoriteViewModelFactory(FavoriteRepository(application))
    }
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        adapter = FavoriteAdapter()
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        adapter.setOnClickCallback(object : FavoriteAdapter.OnClickCallback {
            override fun onItemClick(favUser: FavoriteUser) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_LOGIN,favUser.username)
                startActivity(intent)
            }
        })
        userViewModel.getAllFavoriteUsers().observe(this){
            adapter.setListFavoriteUsers(it)
        }


    }
}