package com.hasnarof.githubuser.ui.userfavorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasnarof.githubuser.R
import com.hasnarof.githubuser.databinding.FragmentUserFavoritesBinding
import com.hasnarof.githubuser.domain.models.UserFavorite
import com.hasnarof.githubuser.helper.ViewModelFactory
import com.hasnarof.githubuser.ui.adapters.ListUserFavoriteAdapter
import com.hasnarof.githubuser.ui.userdetail.UserDetailFragment


class UserFavoritesFragment : Fragment() {

    private var _binding: FragmentUserFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserFavoritesViewModel

    private lateinit var adapter: ListUserFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserFavoritesBinding.inflate(inflater, container, false)
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
        viewModel = ViewModelProvider(this, factory).get(UserFavoritesViewModel::class.java)

        viewModel.getAllUserFavorites().observe(viewLifecycleOwner) { userList ->
            setUsersData(userList)
        }

    }

    private fun setUsersData(userList: List<UserFavorite>) {
        if(userList.isEmpty()) {
            binding.tvSearchDescription.visibility = View.VISIBLE

        } else {
            binding.tvSearchDescription.visibility = View.GONE
        }

        val listUserAdapter = ListUserFavoriteAdapter(userList)
        binding.rvUsers.adapter = listUserAdapter
        listUserAdapter.setOnItemClickCallback(object : ListUserFavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserFavorite) {
                val mBundle = Bundle()
                mBundle.putString(UserDetailFragment.EXTRA_USERNAME, data.username)
                findNavController().navigate(R.id.action_userFavoritesFragment_to_userDetailFragment, mBundle)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}