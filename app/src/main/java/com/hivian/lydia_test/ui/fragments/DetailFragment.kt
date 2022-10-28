package com.hivian.lydia_test.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hivian.lydia_test.R
import com.hivian.lydia_test.databinding.FragmentDetailBinding
import com.hivian.lydia_test.presentation.detail.DetailViewModel
import com.hivian.lydia_test.presentation.detail.DetailViewModelFactory

class DetailFragment : Fragment() {

    private lateinit var binding :  FragmentDetailBinding

    private val args: DetailFragmentArgs by navArgs()

    private val detailViewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(args.randomUser)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = detailViewModel
        }

        return binding.root
    }

}
