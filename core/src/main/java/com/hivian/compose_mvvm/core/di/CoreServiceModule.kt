package com.hivian.compose_mvvm.core.di

import android.app.Application
import com.hivian.compose_mvvm.core.services.ILocalizationService
import com.hivian.compose_mvvm.core.services.IUserInteractionService
import com.hivian.compose_mvvm.core.services.LocalizationService
import com.hivian.compose_mvvm.core.services.UserInteractionService
import org.koin.dsl.module

fun provideLocalizationService(applicationContext: Application): ILocalizationService {
    return LocalizationService(applicationContext)
}

fun provideUserInteractionService(): IUserInteractionService {
    return UserInteractionService()
}

val coreServiceModule = module {
    single { provideLocalizationService(get()) }
    single { provideUserInteractionService() }
}
