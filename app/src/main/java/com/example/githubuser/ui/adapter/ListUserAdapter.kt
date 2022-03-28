package com.example.githubuser.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.network.response.UserResponse

class ListUserAdapter: RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    private val listUser = ArrayList<UserResponse>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setAllData(data: List<UserResponse>) {
        listUser.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class UserViewHolder(private val view: ItemRowUserBinding): RecyclerView.ViewHolder(view.root) {

        @SuppressLint("SetTextI18n")
        fun bind(user: UserResponse) {

            view.apply {
                tvItemUsername.text = "@" + user.username
            }

            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(view.imgItemPhoto)

            view.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponse)
    }

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}