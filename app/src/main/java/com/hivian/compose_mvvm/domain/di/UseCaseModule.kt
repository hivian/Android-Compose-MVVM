package com.hivian.compose_mvvm.domain.di

import com.hivian.compose_mvvm.domain.repository.IRandomUsersRepository
import com.hivian.compose_mvvm.domain.services.ILocalizationService
import com.hivian.compose_mvvm.domain.services.INavigationService
import com.hivian.compose_mvvm.domain.services.IUserInteractionService
import com.hivian.compose_mvvm.domain.usecases.GetRandomUsersUseCase
import com.hivian.compose_mvvm.domain.usecases.NavigateBackUseCase
import com.hivian.compose_mvvm.domain.usecases.NavigateToRandomUserDetailUseCase
import com.hivian.compose_mvvm.domain.usecases.ShowAppMessageUseCase
import com.hivian.compose_mvvm.domain.usecases.TranslateResourceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetRandomUsersUseCase(randomUsersService: IRandomUsersRepository): GetRandomUsersUseCase {
        return GetRandomUsersUseCase(randomUsersService)
    }

    @Provides
    fun provideNavigateToRandomUserDetailUseCase(navigationService: INavigationService): NavigateToRandomUserDetailUseCase {
        return NavigateToRandomUserDetailUseCase(navigationService)
    }

    @Provides
    fun provideNavigateBack(navigationService: INavigationService): NavigateBackUseCase {
        return NavigateBackUseCase(navigationService)
    }

    @Provides
    fun provideTranslateResourceUseCase(localizationService: ILocalizationService): TranslateResourceUseCase {
        return TranslateResourceUseCase(localizationService)
    }

    @Provides
    fun provideShowAppMessageUseCase(userInteractionService: IUserInteractionService): ShowAppMessageUseCase {
        return ShowAppMessageUseCase(userInteractionService)
    }

}
