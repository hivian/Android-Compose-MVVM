package com.hivian.compose_mvvm.data.services

import com.hivian.compose_mvvm.data.models.RandomUserDTO
import com.hivian.compose_mvvm.data.sources.local.dao.IRandomUsersDao
import com.hivian.compose_mvvm.domain.services.IDatabaseService
import javax.inject.Inject

internal class DatabaseServiceImpl @Inject constructor(
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
