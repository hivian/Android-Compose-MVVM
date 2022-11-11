package com.hivian.lydia_test.core.services.database

import android.content.Context
import com.hivian.lydia_test.core.database.RandomUsersDatabase
import com.hivian.lydia_test.core.models.dto.RandomUserDTO


class DatabaseService(applicationContext: Context): IDatabaseService {

    private var database = RandomUsersDatabase.getInstance(applicationContext)

    private val dao = database.randomUsersDao()

    override suspend fun getUserById(userId: Int): RandomUserDTO {
        return dao.getRandomUserById(userId)
    }

    override suspend fun fetchUsers(): List<RandomUserDTO> {
        return dao.getAllRandomUsers()
    }

    override suspend fun upsertUsers(users: List<RandomUserDTO>) {
        dao.upsert(users)
    }

}
