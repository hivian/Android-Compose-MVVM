package com.hivian.lydia_test.core.services.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.hivian.lydia_test.core.database.RandomUsersDatabase
import com.hivian.lydia_test.core.models.dto.RandomUserDTO


class DatabaseService(applicationContext: Context): IDatabaseService {

    private var database = RandomUsersDatabase.getInstance(applicationContext)

    override suspend fun fetchUsers(): List<RandomUserDTO> {
        return database.randomUsersDao().getAllRandomUsers()
    }

    override suspend fun upsertUsers(users: List<RandomUserDTO>) {
        database.randomUsersDao().upsert(users)
    }

}
