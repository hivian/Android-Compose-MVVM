package com.hivian_compose_mvvm.basic_feature.domain.di

import com.hivian.compose_mvvm.core.services.ILocalizationService
import com.hivian.compose_mvvm.core.services.navigation.INavigationService
import com.hivian.compose_mvvm.core.services.IUserInteractionService
import com.hivian_compose_mvvm.basic_feature.domain.repository.IRandomUsersRepository
import com.hivian_compose_mvvm.basic_feature.domain.usecases.GetRandomUsersUseCase
import com.hivian_compose_mvvm.basic_feature.domain.usecases.NavigateBackUseCase
import com.hivian_compose_mvvm.basic_feature.domain.usecases.NavigateToRandomUserDetailUseCase
import com.hivian_compose_mvvm.basic_feature.domain.usecases.ShowAppMessageUseCase
import com.hivian_compose_mvvm.basic_feature.domain.usecases.TranslateResourceUseCase
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
