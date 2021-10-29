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
import com.dicoding.submission1_fundamentalandroid.api.ItemsItem

class ListUserAdapter(private val list: List<ItemsItem>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(listViewHolder: ListViewHolder, position: Int) {
        listViewHolder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ListViewHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView) {
            private val binding = ItemListBinding.bind(itemView)
            fun bind(users: ItemsItem) {

                binding.name.text = users.login
                Glide.with(itemView.context)
                    .load(users.avatarUrl)
                    .apply(RequestOptions().override(55,55))
                    .into(binding.avatar)

                itemView.setOnClickListener {
                    val moveDetail = Intent(it.context, DetailActivity::class.java)
                    moveDetail.putExtra(DetailActivity.EXTRA_USER, users.login)
                    moveDetail.putExtra(DetailActivity.EXTRA_AVATAR, users.avatarUrl)
                    it.context.startActivity(moveDetail)
                }
            }
        }
}