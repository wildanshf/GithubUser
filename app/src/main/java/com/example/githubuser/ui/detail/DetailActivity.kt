package com.example.githubuser.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.ui.adapter.FollowPagerAdapter
import com.example.githubuser.util.Constanta.EXTRA_AVATAR
import com.example.githubuser.util.Constanta.EXTRA_ID
import com.example.githubuser.util.Constanta.EXTRA_USERNAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        if (username != null) {
            viewModel.setUserDetail(username)
            showLoading(true)
        }
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvItemName.text = it.name
                    tvItemUsername.text = "(@${it.username})"
                    tvItemRepo.text = it.repositories.toString()
                    tvItemFollower.text = it.followers.toString()
                    tvItemFollowing.text = it.following.toString()
                    tvItemLocation.text = it.location
                    tvItemCompany.text = it.company

                    Glide.with(this@DetailActivity)
                        .load(it.avatar)
                        .centerCrop()
                        .into(imgItemPhoto)
                }
                showLoading(false)
            }
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkedUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.tglFavorite.isChecked = true
                        isChecked = true
                    }else{
                        binding.tglFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.tglFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addToFavorite(username, id, avatarUrl)
                    }
                }
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.tglFavorite.isChecked = isChecked
        }

        val followPagerAdapter = FollowPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = followPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}