package com.hivian_compose_mvvm.basic_feature.domain.di

import com.hivian.compose_mvvm.core.services.ILocalizationService
import com.hivian.compose_mvvm.core.services.IUserInteractionService
import com.hivian_compose_mvvm.basic_feature.domain.repository.IRandomUsersRepository
import com.hivian_compose_mvvm.basic_feature.domain.usecases.GetRandomUserByIdUseCase
import com.hivian_compose_mvvm.basic_feature.domain.usecases.GetRandomUsersUseCase
import com.hivian_compose_mvvm.basic_feature.domain.usecases.ShowAppMessageUseCase
import com.hivian_compose_mvvm.basic_feature.domain.usecases.TranslateResourceUseCase
import org.koin.dsl.module

fun provideGetRandomUsersUseCase(randomUsersService: IRandomUsersRepository): GetRandomUsersUseCase {
    return GetRandomUsersUseCase(randomUsersService)
}

fun provideGetRandomUserByIdUseCase(randomUsersService: IRandomUsersRepository): GetRandomUserByIdUseCase {
    return GetRandomUserByIdUseCase(randomUsersService)
}

fun provideTranslateResourceUseCase(localizationService: ILocalizationService): TranslateResourceUseCase {
    return TranslateResourceUseCase(localizationService)
}

fun provideShowAppMessageUseCase(userInteractionService: IUserInteractionService): ShowAppMessageUseCase {
    return ShowAppMessageUseCase(userInteractionService)
}

val useCaseModule = module {
    factory { provideGetRandomUsersUseCase(get()) }
    factory { provideGetRandomUserByIdUseCase(get()) }
    factory { provideTranslateResourceUseCase(get()) }
    factory { provideShowAppMessageUseCase(get()) }
}
