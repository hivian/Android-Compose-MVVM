package com.hivian.compose_mvvm.core.di

import android.content.Context
import com.hivian.compose_mvvm.core.services.ILocalizationService
import com.hivian.compose_mvvm.core.services.navigation.INavigationService
import com.hivian.compose_mvvm.core.services.IUserInteractionService
import com.hivian.compose_mvvm.core.services.LocalizationService
import com.hivian.compose_mvvm.core.services.UserInteractionService
import com.hivian.compose_mvvm.core.services.navigation.NavigationService
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
        return LocalizationService(applicationContext)
    }

    @Provides
    @Singleton
    fun provideUserInteractionService(): IUserInteractionService {
        return UserInteractionService()
    }

    @Provides
    @Singleton
    fun provideNavigationService(): INavigationService {
        return NavigationService()
    }

}
