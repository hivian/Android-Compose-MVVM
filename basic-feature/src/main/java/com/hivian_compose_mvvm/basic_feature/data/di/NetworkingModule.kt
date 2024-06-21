package com.hivian_compose_mvvm.basic_feature.data.di

import com.hivian.compose_mvvm.core.datasources.remote.IApiService
import com.hivian_compose_mvvm.basic_feature.data.sources.RandomUsersHttpService
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersHttpService
import org.koin.dsl.module

fun provideHttpClient(apiService: IApiService): IRandomUsersHttpService {
    return RandomUsersHttpService(apiService)
}

val httpServiceModule = module {
    single { provideHttpClient(get()) }
}


