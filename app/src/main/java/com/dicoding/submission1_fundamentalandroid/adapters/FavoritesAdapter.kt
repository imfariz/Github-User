package com.dicoding.submission1_fundamentalandroid.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.submission1_fundamentalandroid.DetailActivity
import com.dicoding.submission1_fundamentalandroid.R
import com.dicoding.submission1_fundamentalandroid.databinding.ItemListBinding
import com.dicoding.submission1_fundamentalandroid.database.User

class FavoritesAdapter(private val listUser: List<User>) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class FavoritesViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(user: User) {
            binding.name.text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .apply(RequestOptions().override(55, 55))
                .into(binding.avatar)

            itemView.setOnClickListener {
                val moveDetail = Intent(it.context, DetailActivity::class.java)
                moveDetail.putExtra(DetailActivity.EXTRA_USER, user.login)
                moveDetail.putExtra(DetailActivity.EXTRA_AVATAR, user.avatarUrl)
                it.context.startActivity(moveDetail)
            }
        }
    }
}