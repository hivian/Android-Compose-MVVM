package com.hivian.lydia_test.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.hivian.lydia_test.R
import com.hivian.lydia_test.business.model.domain.RandomUserDomain
import com.hivian.lydia_test.core.observe
import com.hivian.lydia_test.core.InfiniteScrollListener
import com.hivian.lydia_test.databinding.FragmentHomeBinding
import com.hivian.lydia_test.presentation.home.HomeListViewEvent
import com.hivian.lydia_test.presentation.home.HomeViewModel
import com.hivian.lydia_test.ui.list.RandomUsersListAdapter

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var viewAdapter: RandomUsersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewAdapter = RandomUsersListAdapter(homeViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = homeViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(homeViewModel.data, ::submitList)
        observe(homeViewModel.clickEvent, ::handleEvent)

        with(binding.randomUsersList) {
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            addOnScrollListener(InfiniteScrollListener(homeViewModel))
        }

    }

    private fun handleEvent(homeListViewEvent: HomeListViewEvent) {
        if (homeListViewEvent is HomeListViewEvent.OpenDetailView)
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(homeListViewEvent.randomUser)
            )
    }

    private fun submitList(data: List<RandomUserDomain>) {
        viewAdapter.submitList(data)
    }

}
