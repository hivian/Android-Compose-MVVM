package com.hivian.compose_mvvm.core.di

import com.hivian.compose_mvvm.core.datasources.local.AppDatabase
import com.hivian.compose_mvvm.core.datasources.local.dao.IRandomUsersDao
import org.koin.dsl.module

val localModule = module {
    single<AppDatabase> { AppDatabase.createDatabase(get()) }
    single<IRandomUsersDao> { get<AppDatabase>().randomUsersDao() }
}