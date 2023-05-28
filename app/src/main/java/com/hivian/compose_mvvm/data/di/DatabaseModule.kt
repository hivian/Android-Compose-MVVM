package com.hivian.compose_mvvm.data.di

import android.content.Context
import com.hivian.compose_mvvm.data.sources.local.RandomUsersDatabase
import com.hivian.compose_mvvm.data.sources.local.dao.IRandomUsersDao
import com.hivian.compose_mvvm.domain.services.database.DatabaseService
import com.hivian.compose_mvvm.domain.services.database.IDatabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): RandomUsersDatabase {
        return RandomUsersDatabase.createDatabase(applicationContext)
    }

    @Provides
    @Singleton
    fun provideRoomDao(roomDatabase: RandomUsersDatabase): IRandomUsersDao {
        return roomDatabase.randomUsersDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(randomUsersDao: IRandomUsersDao): IDatabaseService {
        return DatabaseService(randomUsersDao)
    }

}