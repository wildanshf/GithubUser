package com.example.githubuser.ui.followers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.network.response.UserResponse
import com.example.githubuser.ui.adapter.ListUserAdapter
import com.example.githubuser.ui.detail.DetailActivity
import com.example.githubuser.util.Constanta.EXTRA_USERNAME

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private var _binding : FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(EXTRA_USERNAME).toString()
        _binding = FragmentFollowersBinding.bind(view)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserResponse) {
                Intent(activity, DetailActivity::class.java).also {
                    it.putExtra(EXTRA_USERNAME, data.username)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvListUserFollower.setHasFixedSize(true)
            rvListUserFollower.layoutManager = LinearLayoutManager(activity)
            rvListUserFollower.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this)[FollowersViewModel::class.java]
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setAllData(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.followProgressBar.visibility = View.VISIBLE
        }else{
            binding.followProgressBar.visibility = View.GONE
        }
    }

}