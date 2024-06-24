package com.hivian.randomusers.core.di

import com.hivian.randomusers.core.domain.services.ILocalizationService
import com.hivian.randomusers.core.domain.services.IUserInteractionService
import com.hivian.randomusers.core.domain.usecases.ShowAppMessageUseCase
import com.hivian.randomusers.core.domain.usecases.TranslateResourceUseCase
import org.koin.dsl.module

private fun provideTranslateResourceUseCase(localizationService: ILocalizationService): TranslateResourceUseCase {
    return TranslateResourceUseCase(localizationService)
}

private fun provideShowAppMessageUseCase(userInteractionService: IUserInteractionService): ShowAppMessageUseCase {
    return ShowAppMessageUseCase(userInteractionService)
}

val coreUseCaseModule = module {
    factory { provideTranslateResourceUseCase(get()) }
    factory { provideShowAppMessageUseCase(get()) }
}