package com.hivian_compose_mvvm.basic_feature.data.di

import com.hivian_compose_mvvm.basic_feature.data.repository.RandomUsersRepository
import com.hivian_compose_mvvm.basic_feature.domain.repository.IRandomUsersRepository
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersDatabaseService
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersHttpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRandomUsersRepository(database: IRandomUsersDatabaseService, httpClient: IRandomUsersHttpService): IRandomUsersRepository {
        return RandomUsersRepository(database, httpClient)
    }

}
