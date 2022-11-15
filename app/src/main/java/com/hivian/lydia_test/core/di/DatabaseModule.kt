package com.hivian.lydia_test.core.di

import android.content.Context
import com.hivian.lydia_test.core.base.data.database.IRandomUsersDao
import com.hivian.lydia_test.core.base.data.database.RandomUsersDatabase
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

}