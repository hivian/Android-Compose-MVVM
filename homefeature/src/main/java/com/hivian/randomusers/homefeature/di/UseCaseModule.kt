package com.hivian.randomusers.homefeature.di

import com.hivian.randomusers.homefeature.domain.services.IRandomUsersService
import com.hivian.randomusers.homefeature.domain.usecases.GetRandomUserByIdUseCase
import com.hivian.randomusers.homefeature.domain.usecases.GetRandomUsersUseCase
import org.koin.dsl.module

private fun provideGetRandomUsersUseCase(randomUsersService: IRandomUsersService): GetRandomUsersUseCase {
    return GetRandomUsersUseCase(randomUsersService)
}

private fun provideGetRandomUserByIdUseCase(randomUsersService: IRandomUsersService): GetRandomUserByIdUseCase {
    return GetRandomUserByIdUseCase(randomUsersService)
}

val useCaseModule = module {
    factory { provideGetRandomUsersUseCase(get()) }
    factory { provideGetRandomUserByIdUseCase(get()) }
}
