package com.hivian_compose_mvvm.basic_feature.presentation.di

import com.hivian_compose_mvvm.basic_feature.presentation.detail.DetailViewModel
import com.hivian_compose_mvvm.basic_feature.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { userId -> DetailViewModel(userId.get(), get(), get(), get()) }
}
