package com.hasnarof.githubuser.ui.userdetail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class SectionsPagerAdapter(activity: AppCompatActivity, private val currentFragment: Fragment): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val username = currentFragment.arguments?.getString("username")

        var fragment : Fragment? = null
        when (position) {
            0 -> fragment = UserDetailFollowFragment("followers", username)
            1 -> fragment = UserDetailFollowFragment("following", username)
        }
        return fragment as Fragment
    }
}