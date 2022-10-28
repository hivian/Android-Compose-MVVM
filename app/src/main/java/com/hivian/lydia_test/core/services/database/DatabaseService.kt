package com.hivian.lydia_test.core.services.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.hivian.lydia_test.business.db.RandomUsersDatabase
import com.hivian.lydia_test.business.model.dto.RandomUserDTO


class DatabaseService(applicationContext: Context): IDatabaseService {

    private var database = RandomUsersDatabase.getInstance(
        applicationContext,
    )

    override fun fetchAllUsers(): LiveData<List<RandomUserDTO>> {
        return database.randomUsersDao().getAllRandomUsers()
    }

    override suspend fun upsertUsers(users: List<RandomUserDTO>) {
        database.randomUsersDao().upsert(users)
    }

}