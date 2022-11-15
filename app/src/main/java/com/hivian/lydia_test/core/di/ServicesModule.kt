package com.hivian.lydia_test.core.di

import com.hivian.lydia_test.core.services.application.IRandomUsersService
import com.hivian.lydia_test.core.services.application.RandomUsersService
import com.hivian.lydia_test.core.services.database.DatabaseService
import com.hivian.lydia_test.core.services.database.IDatabaseService
import com.hivian.lydia_test.core.services.localization.ILocalizationService
import com.hivian.lydia_test.core.services.localization.LocalizationService
import com.hivian.lydia_test.core.services.navigation.INavigationService
import com.hivian.lydia_test.core.services.navigation.NavigationService
import com.hivian.lydia_test.core.services.networking.HttpClient
import com.hivian.lydia_test.core.services.networking.IHttpClient
import com.hivian.lydia_test.core.services.userinteraction.IUserInteractionService
import com.hivian.lydia_test.core.services.userinteraction.UserInteractionService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppServiceModule {

    @Binds
    @Singleton
    abstract fun provideDatabaseService(databaseService: DatabaseService): IDatabaseService

    @Binds
    @Singleton
    abstract fun provideLocalizationService(localizationService: LocalizationService): ILocalizationService

    @Binds
    @Singleton
    abstract fun provideNavigationService(navigationService: NavigationService): INavigationService

    @Binds
    @Singleton
    abstract fun provideRandomUsersService(randomUsersService: RandomUsersService): IRandomUsersService

    @Binds
    @Singleton
    abstract fun provideUserInteractionService(userInteractionService: UserInteractionService): IUserInteractionService

    @Binds
    @Singleton
    abstract fun provideHttpClient(retrofitHttpClient: HttpClient): IHttpClient

}
