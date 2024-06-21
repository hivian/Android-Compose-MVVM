package com.hivian_compose_mvvm.basic_feature.data.di

import com.hivian.compose_mvvm.core.datasources.local.dao.IRandomUsersDao
import com.hivian_compose_mvvm.basic_feature.data.sources.RandomUsersDatabaseService
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersDatabaseService
import org.koin.dsl.module

fun provideDatabaseService(randomUsersDao: IRandomUsersDao): IRandomUsersDatabaseService {
    return RandomUsersDatabaseService(randomUsersDao)
}

val databaseServiceModule = module {
    single<IRandomUsersDatabaseService> {
        provideDatabaseService(get())
    }

}