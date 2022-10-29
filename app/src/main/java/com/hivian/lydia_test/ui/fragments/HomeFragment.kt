package com.hivian.lydia_test.ui.fragments

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.hivian.lydia_test.R
import com.hivian.lydia_test.core.models.domain.RandomUserDomain
import com.hivian.lydia_test.core.extensions.observe
import com.hivian.lydia_test.core.application.InfiniteScrollListener
import com.hivian.lydia_test.core.base.FragmentBase
import com.hivian.lydia_test.databinding.FragmentHomeBinding
import com.hivian.lydia_test.presentation.home.HomeViewModel
import com.hivian.lydia_test.ui.list.RandomUsersListAdapter

class HomeFragment : FragmentBase<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var viewAdapter: RandomUsersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewAdapter = RandomUsersListAdapter(homeViewModel)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = homeViewModel
        homeViewModel.initialize()

        observe(homeViewModel.items, ::submitList)

        with(viewBinding.randomUsersList) {
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            addOnScrollListener(InfiniteScrollListener(homeViewModel))
        }
    }

    private fun submitList(data: List<RandomUserDomain>) {
        viewAdapter.submitList(data)
    }

}
