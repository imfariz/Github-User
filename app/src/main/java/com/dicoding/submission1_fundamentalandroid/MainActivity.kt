package com.dicoding.submission1_fundamentalandroid

import android.content.Context
import com.dicoding.submission1_fundamentalandroid.adapters.ListUserAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission1_fundamentalandroid.databinding.ActivityMainBinding
import com.dicoding.submission1_fundamentalandroid.api.ItemsItem
import com.dicoding.submission1_fundamentalandroid.models.MainViewModel
import com.dicoding.submission1_fundamentalandroid.settings.Settings
import com.dicoding.submission1_fundamentalandroid.settings.SettingViewModel
import com.dicoding.submission1_fundamentalandroid.settings.SettingsActivity
import com.dicoding.submission1_fundamentalandroid.settings.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = Settings.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.listUsers.observe(this, { listUser ->
            setUsersData(listUser)
        })

        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        binding.btnUser.setOnClickListener {
            mainViewModel.showList(binding.edUser.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                val moveIntent = Intent(this@MainActivity, FavoritesActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.action_settings -> {
                val moveIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(moveIntent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private  fun setUsersData(user: List<ItemsItem>) {
        val adapter = ListUserAdapter(user)
        binding.rvUsers.adapter = adapter
        binding.rvUsers.setHasFixedSize(true)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

