package com.example.githubuser.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.local.FavoriteUser
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.network.response.UserResponse
import com.example.githubuser.ui.adapter.ListUserAdapter
import com.example.githubuser.ui.detail.DetailActivity
import com.example.githubuser.util.Constanta

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FavoriteViewModel


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserResponse) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(Constanta.EXTRA_USERNAME, data.username)
                    it.putExtra(Constanta.EXTRA_ID, data.id)
                    it.putExtra(Constanta.EXTRA_AVATAR, data.avatar)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setAllData(list)
            }
        }

    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<UserResponse>{
        val listUsers = ArrayList<UserResponse>()
        for (user in users) {
            val userMapped = UserResponse(
                user.avatarUrl,
                user.id,
                user.login
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}