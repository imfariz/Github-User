package com.dicoding.submission1_fundamentalandroid.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.submission1_fundamentalandroid.FollowingsFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username = String()

    override fun createFragment(position: Int): Fragment {
        return FollowingsFragment.newInstance(username, position)
    }

    override fun getItemCount(): Int {
        return 2
    }


}