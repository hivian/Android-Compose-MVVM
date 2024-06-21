package com.hivian_compose_mvvm.basic_feature.data.di

import com.hivian_compose_mvvm.basic_feature.data.repository.RandomUsersRepository
import com.hivian_compose_mvvm.basic_feature.domain.repository.IRandomUsersRepository
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersDatabaseService
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersHttpService
import org.koin.dsl.module

fun provideRandomUsersRepository(
    database: IRandomUsersDatabaseService,
    httpClient: IRandomUsersHttpService
): IRandomUsersRepository {
    return RandomUsersRepository(database, httpClient)
}

val repositoryModule = module {
    single { provideRandomUsersRepository(get(), get()) }
}
