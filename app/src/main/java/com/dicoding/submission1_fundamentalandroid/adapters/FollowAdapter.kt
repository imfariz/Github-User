package com.dicoding.submission1_fundamentalandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.submission1_fundamentalandroid.R
import com.dicoding.submission1_fundamentalandroid.databinding.ItemListBinding
import com.dicoding.submission1_fundamentalandroid.api.FollowResponseItem

class FollowAdapter(private val list: ArrayList<FollowResponseItem>) : RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(listViewHolder: ListViewHolder, position: Int) {
        listViewHolder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class ListViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(users: FollowResponseItem) {
            binding.name.text = users.login
            Glide.with(itemView.context)
                .load(users.avatarUrl)
                .apply(RequestOptions().override(55,55))
                .into(binding.avatar)
        }
    }
}