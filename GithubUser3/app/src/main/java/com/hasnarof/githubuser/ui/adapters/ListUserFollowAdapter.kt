package com.hasnarof.githubuser.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hasnarof.githubuser.R
import com.hasnarof.githubuser.domain.models.User

class ListUserFollowAdapter(private val listUser: List<User>): RecyclerView.Adapter<ListUserFollowAdapter.ListViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListUserFollowAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_user_follow, parent, false)

        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (username, avatarUrl) = listUser[position]
        holder.tvUsername.text = "@${username}"

        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .circleCrop()
            .into(holder.imgPhoto)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
    }

}