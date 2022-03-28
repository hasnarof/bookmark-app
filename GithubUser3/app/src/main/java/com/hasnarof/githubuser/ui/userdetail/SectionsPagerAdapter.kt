package com.hasnarof.githubuser.ui.userdetail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class SectionsPagerAdapter(activity: AppCompatActivity, private val currentFragment: Fragment): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val username = currentFragment.arguments?.getString(UserDetailFragment.EXTRA_USERNAME)

        var fragment : Fragment? = null
        when (position) {
            0 -> fragment = UserDetailFollowFragment(UserDetailFragment.TAB_TITLE_FOLLOWERS, username)
            1 -> fragment = UserDetailFollowFragment(UserDetailFragment.TAB_TITLE_FOLLOWING, username)
        }
        return fragment as Fragment
    }
}