package com.hivian_compose_mvvm.basic_feature.data.di

import com.hivian.compose_mvvm.core.datasources.remote.IApiService
import com.hivian_compose_mvvm.basic_feature.data.sources.RandomUsersHttpService
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersHttpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @Singleton
    fun provideHttpClient(apiService: IApiService): IRandomUsersHttpService {
        return RandomUsersHttpService(apiService)
    }

}
