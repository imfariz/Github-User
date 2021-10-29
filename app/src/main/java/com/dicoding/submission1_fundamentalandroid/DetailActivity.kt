package com.dicoding.submission1_fundamentalandroid

import com.dicoding.submission1_fundamentalandroid.adapters.SectionsPagerAdapter
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.submission1_fundamentalandroid.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.dicoding.submission1_fundamentalandroid.api.DetailResponse
import com.dicoding.submission1_fundamentalandroid.database.User
import com.dicoding.submission1_fundamentalandroid.helper.ViewModelFactory
import com.dicoding.submission1_fundamentalandroid.models.DetailViewModel
import com.dicoding.submission1_fundamentalandroid.models.FavoriteViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var fav: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra(EXTRA_USER).toString()
        val ava = intent.getStringExtra(EXTRA_AVATAR).toString()

        val favoriteViewModel = obtainViewModel(this@DetailActivity)

        val detailViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        detailViewModel.showDetail(userName)

        detailViewModel.detailsUser.observe(this, { detailsUser ->
            setDetailsUser(detailsUser)
        })

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = userName

        val orientation: Int = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.viewPager.layoutParams.height = 750
        }

        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        val title = R.string.detail
        setTitle(title)

        fav.let { fav ->
            fav.login = userName
            fav.avatarUrl = ava
        }

        var check = false
        favoriteViewModel.check(userName).observe(this, { count ->
            if (count != null) {
                check = if(count >0) {
                    binding.fabAdd.setImageResource(R.drawable.ic_add)
                    true
                } else {
                    binding.fabAdd.setImageResource(R.drawable.ic_fav)
                    false
                }
            }
        })


        binding.fabAdd.setOnClickListener {
            check = !check
            if (check) {
                favoriteViewModel.insert(fav)
            } else {
                favoriteViewModel.delete(userName)
            }
        }
    }

    private fun setDetailsUser(user: DetailResponse) {
        Glide.with(this)
            .load(user.avatarUrl)
            .apply(RequestOptions()
                .centerCrop()
            ).into(binding.adAvatar)

        binding.apply {
            adName.text = user.name
            adLocation.text = user.location
            adCompany.text = user.company
            adUserName.text = user.login
            adFollowers.text = user.followers.toString()
            adFollowing.text = user.following.toString()
            adRepo.text = user.publicRepos.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    companion object {
        const val EXTRA_USER = "extra_person"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}