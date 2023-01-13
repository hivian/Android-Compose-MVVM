package com.hivian.lydia_test.data.di

import com.hivian.lydia_test.data.services.application.IRandomUsersService
import com.hivian.lydia_test.data.services.application.RandomUsersService
import com.hivian.lydia_test.data.services.database.IDatabaseService
import com.hivian.lydia_test.data.services.networking.IHttpClient
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
