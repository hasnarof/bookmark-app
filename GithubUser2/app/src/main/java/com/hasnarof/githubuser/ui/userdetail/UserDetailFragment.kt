package com.hasnarof.githubuser.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hasnarof.githubuser.databinding.FragmentUserDetailBinding
import com.hasnarof.githubuser.domain.models.UserDetail
import com.hasnarof.githubuser.ui.MainActivity

class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UserDetailViewModel>()

    companion object {
        private const val TAG = "UserDetailFragment"
        private val TAB_TITLES = arrayOf(
            "Followers","Following"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity() as AppCompatActivity, this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        requireActivity().title = "Detail User"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username")
        viewModel.getUser(username)

        viewModel.user.observe(viewLifecycleOwner) {
            setUserData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setUserData(data: UserDetail) {
        Glide.with(this).load(data.avatarUrl).circleCrop().into(binding.imgItemPhoto)

        binding.tvItemUsername.text = "@${data.username}"
        binding.tvItemName.text = data.name
        binding.tvItemBio.text = data.bio
        var company = data.company
        if (data.company == null) {
            company = ""
        }
        var location = data.location
        if (data.location == null) {
            location = ""
        }
        binding.tvItemCompany.text = "${company}"
        binding.tvItemLocation.text = "${location}"
        binding.tvItemFollowers.text = "${data.followers}\nFollowers"
        binding.tvItemFollowing.text = "${data.following}\nFollowing"
        binding.tvItemRepository.text = "${data.publicRepo}\nRepositories"

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}