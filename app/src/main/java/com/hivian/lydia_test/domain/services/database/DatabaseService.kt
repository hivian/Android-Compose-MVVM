package com.hivian.lydia_test.domain.services.database

import com.hivian.lydia_test.data.models.RandomUserDTO
import com.hivian.lydia_test.data.sources.local.dao.IRandomUsersDao
import javax.inject.Inject

internal class DatabaseService @Inject constructor(
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
