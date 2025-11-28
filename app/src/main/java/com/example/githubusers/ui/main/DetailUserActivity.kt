package com.example.githubusers.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubusers.R
import com.example.githubusers.data.local.FavoriteUser
import com.example.githubusers.data.model.DetailUserViewModel
import com.example.githubusers.data.model.FavoriteViewModelFactory
import com.example.githubusers.data.model.ViewModelFactory
import com.example.githubusers.databinding.ActivityDetailUserBinding
import com.example.githubusers.ui.adapter.SectionPagerAdapter
import com.example.githubusers.ui.favorite.FavoriteRepository

import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailUserViewModel>(){
        FavoriteViewModelFactory(FavoriteRepository(application))
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(EXTRA_LOGIN)

        detailViewModel.getDetailUser(username.toString())

        detailViewModel.detailUser.observe(this) {

            with(binding) {
                tvName.text = it.name
                tvUsername.text = "@"+it.login
                tvFollowers.text = it.followers.toString()+"Followers"
                tvFollowing.text = it.following.toString()+"Following"
                Glide.with(binding.root)
                    .load(it.avatarUrl)
                    .into(binding.ivProfile)
                    .clearOnDetach()
            }
        }

        detailViewModel.getFavoriteUserByUsername(username.toString()).observe(this) { favUser ->

            if (favUser != null) {
                binding.toggleFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.toggleFavorite.setImageResource(R.drawable.ic_favoriteborder)
            }

            binding.toggleFavorite.setOnClickListener {
                val favoriteUser = FavoriteUser()
                if (favUser == null) {
                    favoriteUser.username = username.toString()
                    favoriteUser.avatarUrl = detailViewModel.detailUser.value?.avatarUrl
                    detailViewModel.addFavoriteUser(favoriteUser = favoriteUser)
                } else {
                    detailViewModel.deleteFavoriteUser(favUser)
                }
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs

        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    private fun showLoading(isLoading: Boolean) {
        binding.pbProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    companion object {
        const val EXTRA_LOGIN = "login"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following,
        )
    }
}