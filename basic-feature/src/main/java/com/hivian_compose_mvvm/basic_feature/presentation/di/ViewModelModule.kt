package com.hivian_compose_mvvm.basic_feature.presentation.di

import com.hivian_compose_mvvm.basic_feature.presentation.detail.DetailViewModel
import com.hivian_compose_mvvm.basic_feature.presentation.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { HomeViewModel(get(), get(), get()) }
    factory { userId -> DetailViewModel(userId.get(), get(), get(), get()) }
}
