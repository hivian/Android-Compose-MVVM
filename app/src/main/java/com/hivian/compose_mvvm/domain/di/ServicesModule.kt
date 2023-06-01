package com.hivian.compose_mvvm.domain.di

import android.content.Context
import com.hivian.compose_mvvm.domain.repository.IRandomUsersRepository
import com.hivian.compose_mvvm.data.repository.RandomUsersRepositoryImpl
import com.hivian.compose_mvvm.data.services.LocalizationServiceImpl
import com.hivian.compose_mvvm.data.services.UserInteractionServiceImpl
import com.hivian.compose_mvvm.domain.services.IDatabaseService
import com.hivian.compose_mvvm.domain.services.IHttpClient
import com.hivian.compose_mvvm.domain.services.ILocalizationService
import com.hivian.compose_mvvm.domain.services.IUserInteractionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    @Singleton
    fun provideLocalizationService(@ApplicationContext applicationContext: Context): ILocalizationService {
        return LocalizationServiceImpl(applicationContext)
    }

    @Provides
    @Singleton
    fun provideUserInteractionService(): IUserInteractionService {
        return UserInteractionServiceImpl()
    }

    @Provides
    @Singleton
    fun provideRandomUsersRepository(database: IDatabaseService, httpClient: IHttpClient): IRandomUsersRepository {
        return RandomUsersRepositoryImpl(database, httpClient)
    }

}
