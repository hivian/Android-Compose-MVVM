package com.hivian.android_mvvm_compose.ui.di

import com.hivian.android_mvvm_compose.ui.services.navigation.INavigationService
import com.hivian.android_mvvm_compose.ui.services.navigation.NavigationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideNavigationService(): INavigationService {
        return NavigationService()
    }

}
