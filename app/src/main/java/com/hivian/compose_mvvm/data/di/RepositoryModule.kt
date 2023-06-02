package com.hivian.compose_mvvm.data.di

import com.hivian.compose_mvvm.data.repository.RandomUsersRepository
import com.hivian.compose_mvvm.domain.repository.IRandomUsersRepository
import com.hivian.compose_mvvm.domain.services.IDatabaseService
import com.hivian.compose_mvvm.domain.services.IHttpClient
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
    fun provideRandomUsersRepository(database: IDatabaseService, httpClient: IHttpClient): IRandomUsersRepository {
        return RandomUsersRepository(database, httpClient)
    }

}
