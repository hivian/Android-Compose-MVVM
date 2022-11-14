package com.hivian.lydia_test.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hivian.lydia_test.core.models.dto.RandomUserDTO

@Database(entities = [RandomUserDTO::class], version = RandomUsersDatabase.DB_VERSION, exportSchema = false)
@TypeConverters(NameConverter::class, LocationConverter::class, PictureConverter::class)
abstract class RandomUsersDatabase : RoomDatabase() {

    abstract fun randomUsersDao() : IRandomUsersDao

    companion object {
        private const val DB_NAME = "random_users_database"

        const val DB_VERSION = 1

        fun createDatabase(context: Context): RandomUsersDatabase {
            return Room.databaseBuilder(
                context,
                RandomUsersDatabase::class.java,
                DB_NAME
            ).build()
        }

    }

}
