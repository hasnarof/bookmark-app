package com.hasnarof.githubuser.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hasnarof.githubuser.R
import com.hasnarof.githubuser.databinding.FragmentUserDetailBinding
import com.hasnarof.githubuser.domain.models.UserDetail
import com.hasnarof.githubuser.domain.models.UserFavorite
import com.hasnarof.githubuser.helper.ViewModelFactory

class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private var isFavorite: Boolean = false
    private lateinit var userDetail: UserDetail

    private lateinit var viewModel: UserDetailViewModel

    companion object {
        private const val TAG = "UserDetailFragment"
        const val TAB_TITLE_FOLLOWERS = "Followers"
        const val TAB_TITLE_FOLLOWING = "Following"
        const val EXTRA_TAB_TITLE = "tab_title"
        const val EXTRA_USERNAME = "username"
        private val TAB_TITLES = arrayOf(
            TAB_TITLE_FOLLOWERS,TAB_TITLE_FOLLOWING
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username")

        val factory = ViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory).get(UserDetailViewModel::class.java)

        viewModel.getUser(username)

        viewModel.user.observe(viewLifecycleOwner) {
            setUserData(it)
            userDetail = it
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.isFavorite(username).observe(viewLifecycleOwner) {
            if (it.size > 0 ) {
                setFavoriteLogo(true)
                isFavorite = true
            } else {
                setFavoriteLogo(false)
                isFavorite = false
            }
        }

        binding.ivFavorite.setOnClickListener {
            if (isFavorite) {
                viewModel.deleteUserFavorite(username)
            } else {
                val userFavorite = UserFavorite()
                userFavorite.username = username
                userFavorite.avatarUrl = userDetail.avatarUrl
                viewModel.insertUserFavorite(userFavorite)
            }
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

    private fun setFavoriteLogo(isFavorite: Boolean) {
        if(isFavorite) {
            binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(binding.ivFavorite.context, R.drawable.ic_baseline_star_white_24))
        } else {
            binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(binding.ivFavorite.context, R.drawable.ic_baseline_star_border_white_24))
        }
    }

}