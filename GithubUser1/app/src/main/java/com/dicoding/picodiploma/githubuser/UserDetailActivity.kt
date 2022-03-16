package com.dicoding.picodiploma.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide

import android.view.View
import android.widget.ImageView


class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val actionBar = supportActionBar
        actionBar!!.title = "Detail User"

        val user = intent.getParcelableExtra<User>(EXTRA_PERSON) as User

        val tvName: TextView = findViewById(R.id.tv_item_name)
        val tvUsername: TextView = findViewById(R.id.tv_item_username)
        val tvFollowers: TextView = findViewById(R.id.tv_item_followers)
        val tvFollowing: TextView = findViewById(R.id.tv_item_following)
        val tvRepository: TextView = findViewById(R.id.tv_item_repository)
        val tvCompanyLocation: TextView = findViewById(R.id.tv_item_company_location)
        val imgAvatar: ImageView = findViewById(R.id.img_item_photo) as ImageView

        tvName.text = user.name
        tvUsername.text = "@${user.username}"
        tvFollowers.text = "${user.followers.toString()}\n Followers"
        tvFollowing.text = "${user.following.toString()}\n Following"
        tvRepository.text = "${user.repository.toString()}\n Repositories"
        tvCompanyLocation.text = "${user.company} | ${user.location}"
        Glide.with(this).load(user.avatar).circleCrop().into(imgAvatar)
    }
}