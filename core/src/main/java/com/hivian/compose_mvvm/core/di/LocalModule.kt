package com.hivian.compose_mvvm.core.di

import android.content.Context
import com.hivian.compose_mvvm.core.datasource.local.AppDatabase
import com.hivian.compose_mvvm.core.datasource.local.dao.IRandomUsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return AppDatabase.createDatabase(applicationContext)
    }

    @Provides
    @Singleton
    fun provideRoomDao(roomDatabase: AppDatabase): IRandomUsersDao {
        return roomDatabase.randomUsersDao()
    }

}