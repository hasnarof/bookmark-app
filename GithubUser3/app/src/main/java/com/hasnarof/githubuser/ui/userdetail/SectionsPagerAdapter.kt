package com.hasnarof.githubuser.ui.userdetail

import android.os.Bundle
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
        val args = Bundle()
        args.putString(UserDetailFragment.EXTRA_USERNAME, username)
        when (position) {
            0 -> {
                fragment =
                    UserDetailFollowFragment()
                args.putString(UserDetailFragment.EXTRA_TAB_TITLE, UserDetailFragment.TAB_TITLE_FOLLOWERS)
            }
            1 -> {
                fragment =
                    UserDetailFollowFragment()
                args.putString(UserDetailFragment.EXTRA_TAB_TITLE, UserDetailFragment.TAB_TITLE_FOLLOWING)

            }
        }
        fragment!!.arguments = args
        return fragment as Fragment
    }
}