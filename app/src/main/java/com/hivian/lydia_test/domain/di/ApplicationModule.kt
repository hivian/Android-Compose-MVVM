package com.hivian.lydia_test.domain.di

import com.hivian.lydia_test.domain.services.application.IRandomUsersService
import com.hivian.lydia_test.domain.services.application.RandomUsersService
import com.hivian.lydia_test.domain.services.database.IDatabaseService
import com.hivian.lydia_test.domain.services.networking.IHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideRandomUsersService(database: IDatabaseService, httpClient: IHttpClient): IRandomUsersService {
        return RandomUsersService(database, httpClient)
    }

}
