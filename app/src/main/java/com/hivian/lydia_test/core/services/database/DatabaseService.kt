package com.hivian.lydia_test.core.services.database

import com.hivian.lydia_test.core.database.IRandomUsersDao
import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import javax.inject.Inject

class DatabaseService @Inject constructor(
    private val dao: IRandomUsersDao
): IDatabaseService {

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
