package com.hivian.randomusers.homefeature.di

import com.hivian.randomusers.core.domain.services.IApiService
import com.hivian.randomusers.core.domain.services.IDatabaseService
import com.hivian.randomusers.homefeature.data.services.RandomUsersService
import com.hivian.randomusers.homefeature.domain.services.IRandomUsersService
import org.koin.dsl.module

private fun provideRandomUsersRepository(
    database: IDatabaseService,
    httpClient: IApiService
): IRandomUsersService {
    return RandomUsersService(database, httpClient)
}

val repositoryModule = module {
    single { provideRandomUsersRepository(get(), get()) }
}
