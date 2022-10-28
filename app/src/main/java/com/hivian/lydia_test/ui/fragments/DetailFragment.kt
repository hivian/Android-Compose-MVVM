package com.hivian.lydia_test.ui.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hivian.lydia_test.R
import com.hivian.lydia_test.core.services.base.FragmentBase
import com.hivian.lydia_test.databinding.FragmentDetailBinding
import com.hivian.lydia_test.presentation.detail.DetailViewModel
import com.hivian.lydia_test.presentation.detail.DetailViewModelFactory

class DetailFragment : FragmentBase<FragmentDetailBinding>(
    R.layout.fragment_detail
) {

    private val args: DetailFragmentArgs by navArgs()

    private val detailViewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(args.randomUser)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = detailViewModel
    }

}
