package com.hivian_compose_mvvm.basic_feature.data.sources

import com.hivian.compose_mvvm.core.datasources.local.dao.IRandomUsersDao
import com.hivian.compose_mvvm.core.datasources.models.RandomUserDTO
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersDatabaseService

internal class RandomUsersDatabaseService(
    private val dao: IRandomUsersDao
): IRandomUsersDatabaseService {

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
