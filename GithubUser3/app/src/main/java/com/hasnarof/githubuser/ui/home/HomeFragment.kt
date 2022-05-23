package com.hasnarof.githubuser.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasnarof.githubuser.R
import com.hasnarof.githubuser.databinding.FragmentHomeBinding
import com.hasnarof.githubuser.domain.models.User
import com.hasnarof.githubuser.ui.adapters.ListUserAdapter
import com.hasnarof.githubuser.ui.userdetail.UserDetailFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<User>()
    private val viewModel by viewModels<HomeViewModel>()

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(true)

        val searchView = binding.search

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listUsers.observe(viewLifecycleOwner) {
            setUsersData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setUsersData(userList: List<User>) {
        if(userList.isEmpty()) {
            binding.tvSearchDescription.text = "User not found."
            binding.tvSearchDescription.visibility = View.VISIBLE

        } else {
            binding.tvSearchDescription.visibility = View.GONE
        }

        val listUserAdapter = ListUserAdapter(userList)
        binding.rvUsers.adapter = listUserAdapter
        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val mBundle = Bundle()
                mBundle.putString(UserDetailFragment.EXTRA_USERNAME, data.username)
                findNavController().navigate(R.id.action_homeFragment_to_userDetailFragment, mBundle)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}