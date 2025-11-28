package com.example.githubusers.ui.favorite

import com.example.githubusers.data.local.FavoriteUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.databinding.ItemUserBinding


class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val listFavoriteUsers = ArrayList<FavoriteUser>()
    fun setListFavoriteUsers(listFavoriteUsers: List<FavoriteUser>) {
        val diffCallBack = FavoriteUserDIffCallback(this.listFavoriteUsers, listFavoriteUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        this.listFavoriteUsers.clear()
        this.listFavoriteUsers.addAll(listFavoriteUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                Glide
                    .with(binding.root)
                    .load(favoriteUser.avatarUrl)
                    .circleCrop()
                    .into(ivUser)
                tvUsername.text = favoriteUser.username
            }
        }
    }

    interface OnClickCallback {
        fun onItemClick(favUser: FavoriteUser)
    }

    private lateinit var onItemClickCallback: OnClickCallback

    fun setOnClickCallback(onItemClickCallback: OnClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavoriteUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClick(listFavoriteUsers[position]) }
        holder.bind(listFavoriteUsers[position])
    }
}