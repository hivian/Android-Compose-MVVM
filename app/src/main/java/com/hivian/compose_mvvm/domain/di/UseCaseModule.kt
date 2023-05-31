package com.hivian.compose_mvvm.domain.di

import com.hivian.compose_mvvm.domain.repository.IRandomUsersRepository
import com.hivian.compose_mvvm.data.repository.RandomUsersRepositoryImpl
import com.hivian.compose_mvvm.data.services.database.IDatabaseService
import com.hivian.compose_mvvm.data.services.networking.IHttpClient
import com.hivian.compose_mvvm.domain.usecases.GetRandomUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetRandomUsersUseCase(randomUsersService: IRandomUsersRepository): GetRandomUsersUseCase {
        return GetRandomUsersUseCase(randomUsersService)
    }

}
