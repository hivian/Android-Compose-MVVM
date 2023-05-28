package com.hivian.android_mvvm_compose.core.di

import android.content.Context
import com.hivian.android_mvvm_compose.core.services.localization.ILocalizationService
import com.hivian.android_mvvm_compose.core.services.localization.LocalizationService
import com.hivian.android_mvvm_compose.core.services.userinteraction.IUserInteractionService
import com.hivian.android_mvvm_compose.core.services.userinteraction.UserInteractionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

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

}
