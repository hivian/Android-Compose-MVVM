package com.hivian.lydia_test.ui.di

import com.hivian.lydia_test.ui.services.navigation.INavigationService
import com.hivian.lydia_test.ui.services.navigation.NavigationService
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
