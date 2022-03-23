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

class ListUserAdapter(private val listUser: List<User>
                      ): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (username, avatarUrl, followersUrl, followingUrl) = listUser[position]
        holder.tvName.text = username
        holder.tvUsername.text = "@${username}"

        Glide.with(holder.itemView.context)
            .load(avatarUrl) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(holder.imgPhoto) // imageView mana yang akan diterapkan

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)


    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }


}