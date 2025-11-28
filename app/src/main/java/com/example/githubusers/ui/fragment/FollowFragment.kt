package com.example.githubusers.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.data.model.FollowViewModel
import com.example.githubusers.data.response.ItemsItem
import com.example.githubusers.databinding.FragmentFollowBinding
import com.example.githubusers.ui.adapter.UserAdapter
import com.example.githubusers.ui.main.DetailUserActivity

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private val followViewModel by viewModels<FollowViewModel>()

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = requireActivity().intent.getStringExtra(DetailUserActivity.EXTRA_LOGIN)

        val layoutManager = LinearLayoutManager(context)
        binding.rvUser.layoutManager = layoutManager

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.pbProgressbar.isVisible = it

        }
        if (index == 1) {
            followViewModel.getFollowers(username.toString())
            followViewModel.listFollowers.observe(viewLifecycleOwner) {
                setAdapter(it)
            }
        } else {
            followViewModel.getFollowings(username.toString())
            followViewModel.listFollowings.observe(viewLifecycleOwner) {
                setAdapter(it)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
    private fun setAdapter(user: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
    }
}