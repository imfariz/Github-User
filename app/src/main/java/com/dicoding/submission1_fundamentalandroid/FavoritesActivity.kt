package com.dicoding.submission1_fundamentalandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission1_fundamentalandroid.adapters.FavoritesAdapter
import com.dicoding.submission1_fundamentalandroid.database.User
import com.dicoding.submission1_fundamentalandroid.databinding.ActivityFavoriteBinding
import com.dicoding.submission1_fundamentalandroid.helper.ViewModelFactory
import com.dicoding.submission1_fundamentalandroid.models.FavoriteViewModel

class FavoritesActivity : AppCompatActivity() {

    private var _activityFavoritesBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoritesBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val favoriteViewModel = obtainViewModel(this@FavoritesActivity)
        favoriteViewModel.getAllUsers().observe(this, { userList ->
            if (userList != null) {
                if (userList.toString() != "[]") {
                    binding?.tvNf?.visibility = View.GONE
                    setUsersData(userList)
                } else {
                    binding?.tvNf?.visibility = View.VISIBLE
                }
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding?.rvFavs?.layoutManager = layoutManager
    }

    private fun setUsersData(user: List<User>) {
        val adapter = FavoritesAdapter(user)
        binding?.rvFavs?.adapter = adapter
        binding?.rvFavs?.setHasFixedSize(true)
    }

    override fun onResume() {
        val favoriteViewModel = obtainViewModel(this@FavoritesActivity)
        favoriteViewModel.getAllUsers().observe(this, {
            setUsersData(it)
        })
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoritesBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

}