package com.hivian_compose_mvvm.basic_feature.data.di

import com.hivian.compose_mvvm.core.datasources.local.dao.IRandomUsersDao
import com.hivian_compose_mvvm.basic_feature.data.sources.RandomUsersDatabaseService
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersDatabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseService(randomUsersDao: IRandomUsersDao): IRandomUsersDatabaseService {
        return RandomUsersDatabaseService(randomUsersDao)
    }

}