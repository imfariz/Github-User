package com.dicoding.submission1_fundamentalandroid

import com.dicoding.submission1_fundamentalandroid.adapters.FollowAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.submission1_fundamentalandroid.models.DetailViewModel


class FollowingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvFollowing)
        val progress: ProgressBar = view.findViewById(R.id.progressBarFollowing)
        val layoutManager = LinearLayoutManager(view.context)

        val userName = arguments?.getString(ARG_USERNAME)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)

        userName?.let { detailViewModel.getFollowing(it) }
        userName?.let { detailViewModel.getFollower(it) }

        if (index == 0) {
            detailViewModel.following.observe(viewLifecycleOwner, { following ->
                val adapter = FollowAdapter(following)
                recyclerView.adapter = adapter
            })
        } else {
            detailViewModel.followers.observe(viewLifecycleOwner, { followers ->
                val adapter = FollowAdapter(followers)
                recyclerView.adapter = adapter
            })
        }

        detailViewModel.isLoadingFollow.observe(viewLifecycleOwner, { isLoadingFollow ->
            if (!isLoadingFollow) {
                progress.visibility = View.GONE
            } else progress.visibility = View.VISIBLE
        })
    }

    companion object {

        private const val ARG_USERNAME = "username"
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(username: String, index: Int) =
            FollowingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }
}