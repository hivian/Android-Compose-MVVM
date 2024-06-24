package com.hivian.randomusers.core.data.local

import com.hivian.randomusers.core.data.local.dao.IRandomUsersDao
import com.hivian.randomusers.core.data.models.RandomUserDTO
import com.hivian.randomusers.core.domain.services.IDatabaseService

internal class DatabaseService(
    private val dao: IRandomUsersDao
): IDatabaseService {

    override suspend fun getUserById(userId: Int): RandomUserDTO {
        return dao.getRandomUserById(userId)
    }

    override suspend fun fetchUsers(pageIndex: Int, pageSize: Int): List<RandomUserDTO> {
        return dao.getAllRandomUsers((pageIndex - 1) * pageSize, pageSize)
    }

    override suspend fun upsertUsers(users: List<RandomUserDTO>) {
        dao.upsert(users)
    }

}
