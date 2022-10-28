package com.hivian.lydia_test.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hivian.lydia_test.business.model.domain.RandomUserDomain

class DetailViewModelFactory(private val randomUser: RandomUserDomain) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(randomUser) as T
    }

}
