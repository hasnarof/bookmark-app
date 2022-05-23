package com.hasnarof.githubuser.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasnarof.githubuser.R
import com.hasnarof.githubuser.databinding.FragmentUserDetailFollowBinding
import com.hasnarof.githubuser.domain.models.User
import com.hasnarof.githubuser.helper.ViewModelFactory
import com.hasnarof.githubuser.ui.adapters.ListUserFollowAdapter

class UserDetailFollowFragment() : Fragment() {

    private var _binding: FragmentUserDetailFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabTitle: String
    private lateinit var username: String

    companion object {
        private const val TAG = "UserDetailFollowFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailFollowBinding.inflate(inflater, container, false)
        val view = binding.root

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, factory).get(UserDetailViewModel::class.java)

        tabTitle = arguments?.getString(UserDetailFragment.EXTRA_TAB_TITLE).toString();
        username = arguments?.getString(UserDetailFragment.EXTRA_USERNAME).toString()

        if (tabTitle == UserDetailFragment.TAB_TITLE_FOLLOWERS) {
            viewModel.getFollowers(username)
            viewModel.followers.observe(viewLifecycleOwner) {
                setUsersData(it)
            }
        } else {
            viewModel.getFollowing(username)
            viewModel.following.observe(viewLifecycleOwner) {
                setUsersData(it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setUsersData(userList: List<User>) {
        if(userList.isEmpty()) {
            binding.tvNotFound.text = if (tabTitle == UserDetailFragment.TAB_TITLE_FOLLOWERS) getString(
                R.string.followers_not_found) else getString(R.string.following_not_found)
            binding.tvNotFound.visibility = View.VISIBLE
        } else {
            binding.tvNotFound.visibility = View.GONE
        }

        val listUserFollowAdapter = ListUserFollowAdapter(userList)
        binding.rvUsers.adapter = listUserFollowAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}