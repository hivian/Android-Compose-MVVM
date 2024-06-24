package com.hivian.randomusers.core.di

import android.content.Context
import com.hivian.randomusers.core.data.local.AppDatabase
import com.hivian.randomusers.core.data.local.DatabaseService
import com.hivian.randomusers.core.data.local.dao.IRandomUsersDao
import com.hivian.randomusers.core.domain.services.IDatabaseService
import org.koin.dsl.module

private fun provideRandomUsersDao(appDatabase: AppDatabase): IRandomUsersDao {
    return appDatabase.randomUsersDao()
}

private fun provideDatabaseClient(context: Context): AppDatabase {
    return AppDatabase.createDatabase(context)
}

private fun provideDatabaseService(randomUsersDao: IRandomUsersDao): IDatabaseService {
    return DatabaseService(randomUsersDao)
}

val localModule = module {
    single<IRandomUsersDao> { provideRandomUsersDao(get()) }
    single { provideDatabaseClient(get()) }
    single<IDatabaseService> { provideDatabaseService(get()) }
}
