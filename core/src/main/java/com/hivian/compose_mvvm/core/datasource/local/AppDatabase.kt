package com.hivian.compose_mvvm.core.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hivian.compose_mvvm.core.datasource.local.converters.LocationConverter
import com.hivian.compose_mvvm.core.datasource.local.converters.NameConverter
import com.hivian.compose_mvvm.core.datasource.local.converters.PictureConverter
import com.hivian.compose_mvvm.core.datasource.local.dao.IRandomUsersDao
import com.hivian.compose_mvvm.core.datasource.models.RandomUserDTO

@Database(entities = [RandomUserDTO::class], version = AppDatabase.DB_VERSION, exportSchema = false)
@TypeConverters(NameConverter::class, LocationConverter::class, PictureConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun randomUsersDao() : IRandomUsersDao

    companion object {
        private const val DB_NAME = "app_database"

        const val DB_VERSION = 1

        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }

    }

}
