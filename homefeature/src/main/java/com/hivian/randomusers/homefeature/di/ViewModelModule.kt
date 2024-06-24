package com.hivian.randomusers.homefeature.di

import com.hivian.randomusers.homefeature.presentation.detail.DetailViewModel
import com.hivian.randomusers.homefeature.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { userId -> DetailViewModel(userId.get(), get(), get(), get()) }
}
